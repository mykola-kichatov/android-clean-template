# AGENTS.md

This file provides guidance to AI coding agents (Claude Code, Codex, Cursor, Gemini CLI, Copilot, …) when working with code in this repository. `CLAUDE.md` is a one-line import pointer to this file — keep it that way.

## Fresh clone?

If the application id is still `com.mkchtv.cleantemplate`, this clone hasn't been rebranded — execute [docs/CLONE_CHECKLIST.md](docs/CLONE_CHECKLIST.md) first, interviewing the user for the decisions it requires. In Claude Code, the `/rebrand` skill does exactly that. (The checklist's final step deletes this section.)

## Build & Development Commands

```bash
./gradlew build                    # Full build
./gradlew test                     # All unit tests
./gradlew :module:test             # Unit tests for a specific module
./gradlew connectedAndroidTest     # Instrumented tests (requires device/emulator)
./gradlew ktlint                   # Check code style
./gradlew ktlintFormat             # Auto-fix code style
./gradlew assemble                 # Build APKs/AARs
```

Example for a specific module: `./gradlew :data:element:test`

## Architecture

Clean Architecture with strict layer separation enforced by module boundaries:

```
domain/* (pure Kotlin, no Android)
    ↑ implemented by
data/* (Room, Retrofit, repository impls)
    ↑ consumed by
ui/* (MVVM + Jetpack Compose ViewModels/screens)
    ↑ wired by
app/ (Hilt entry point, navigation host, Application class)
```

### Domain Layer
- Contains only interfaces (repository contracts) and use cases
- Use cases are callable classes with `operator fun invoke()`
- No Android or external dependencies — only `javax.inject` and coroutines

### Data Layer
- Implements domain repository interfaces (`DefaultXxxRepository : XxxRepository`)
- Room for local persistence; Retrofit + Moshi for network
- Hilt `@Module` with `@Binds` wires interface → implementation at `SingletonComponent` scope
- KSP generates Room and Moshi code

### UI Layer
- `@HiltViewModel` ViewModels inject domain use cases directly
- ViewModels expose `StateFlow` (mapped from domain `Flow`) for Compose to collect
- Use `SharingStarted.Lazily` so the flow survives back-stack navigation without restarting
- Screens are `@Composable` functions; no business logic in composables

## Dependency Injection

Hilt throughout. Custom coroutine scope qualifiers live in `domain/common/di/Qualifiers.kt`:
- `@AppIoScope` — for IO-bound work
- `@AppDefaultScope` — for CPU-bound work

## Navigation

Two-level Jetpack Navigation Compose:
- `AppNavHost` (app module) — top-level: `HOME` and `ElementDetails` destinations
- `HomeNavHost` (ui/home) — nested bottom-nav tabs: `ElementList`, `Settings`

Shared element transitions use `SharedTransitionLayout` exposed via `LocalSharedTransitionScope` composition local.

## Testing

JUnit 5 (Jupiter) + MockK. Pattern used across the project:

```kotlin
@TestInstance(Lifecycle.PER_CLASS)
class FooTest {
    @MockK lateinit var mockDep: Dep

    @BeforeAll fun setup() { MockKAnnotations.init(this, relaxUnitFun = true) }

    @Test
    fun `description`() = runTest {
        // [GIVEN]
        // [WHEN]
        // [THEN]
    }
}
```

## Module Convention Plugins (build-logic)

Each module type uses a convention plugin instead of repeating build config:
- `com.mkchtv.convention.domain` — pure Kotlin/JVM library
- `com.mkchtv.convention.data` — Android library + Room + Retrofit + Hilt + unit tests
- `com.mkchtv.convention.ui` — Android library + Compose + Hilt + unit tests
- `com.mkchtv.convention.app` — application + all of the above + instrumented tests

SDK/version constants are in `build-logic/convention/src/main/kotlin/Constants.kt` (minSdk 24, compileSdk/targetSdk 36, Java 17).

All dependency versions are managed centrally in `gradle/libs.versions.toml`.

## MVI contract

Each UI feature has a `<Feature>Contract.kt` with three types — convention only, no base class:

```kotlin
@Immutable
internal data class UiState(...)       // current screen state; `@Immutable` required for Compose

internal sealed interface Intent { ... } // user-initiated events sent to ViewModel

internal sealed interface Effect { ... } // one-shot effects (navigate, show error)
```

ViewModel shape:
- `StateFlow<UiState>` built with `.stateIn(SharingStarted.Lazily)` — survives back-stack
- `Channel<Effect>` exposed as `receiveAsFlow()` — one-shot, collected in `LaunchedEffect`
- Single entry point `fun onIntent(intent: Intent)` dispatching a `when` expression

Reference: `ui/element-list/.../ElementListContract.kt` + `ElementListViewModel.kt`

## Coroutine scopes in ViewModels

Two scopes, different lifetimes:
- `viewModelScope` — for navigation effects and anything that should cancel if the user leaves the screen
- `@AppIoScope` (injected `CoroutineScope`) — for fire-and-forget writes (DB/network) that must survive ViewModel death; uses `SupervisorJob + Dispatchers.IO`

```kotlin
// fire-and-forget write: appIoScope so it outlives the ViewModel
appIoScope.launch { deleteElement(elementId) }
// navigation: viewModelScope so it cancels if screen is gone
viewModelScope.launch { _effects.send(Effect.NavigateBack) }
```

Reference: `ui/element-details/.../ElementDetailsViewModel.kt:47-58`

Note: the `@AppIoScope` / `@AppDefaultScope` qualifiers are defined in `domain/common/di/Qualifiers.kt` but the actual `CoroutineScope` bindings are provided in `ui/common/di/CoroutinesModule.kt`.

## Module file layout

Every UI feature module uses four files per destination:

```
<Feature>Contract.kt   — UiState / Intent / Effect (all internal)
<Feature>ViewModel.kt  — @HiltViewModel, internal, holds business logic
<Feature>Screen.kt     — stateless @Composable, no logic
<Feature>Nav.kt        — NavGraphBuilder extension + NavController extension + route/arg constants
```

Sub-package conventions inside a module: `component/`, `di/`, `entity/`, `mapper/`, `usecase/`, `repository/`, `dao/`, `db/`, `network/`, `extension(s)/`

Visibility rule: ViewModels, Contracts, Repository impls, Room entities/DAOs → `internal`. Domain entities, use cases, repository interfaces → `public`.

Auth-gating: wrap the destination body in `AuthProtectedScreen { ... }` (`:ui:auth`). See `ElementDetailsNav.kt:34`.

## Adding a new feature module

1. Create `domain/<name>/` — convention plugin `com.mkchtv.convention.domain`; add repo interface + use cases
2. Create `data/<name>/` — convention plugin `com.mkchtv.convention.data`; implement repo + Room/Retrofit DI modules
3. Create `ui/<name>-*/` — convention plugin `com.mkchtv.convention.ui`; add Contract + ViewModel + Screen + Nav
4. Register all new modules in `settings.gradle.kts` (follow the `include(":domain:element")` pattern at lines 23-37)
5. Add `implementation(project(":..."))` for each new module in `app/build.gradle.kts`

Reference feature spanning all layers: `:domain:element` → `:data:element` → `:ui:element-list` + `:ui:element-details`

## Known template debt

These two items exist in the template as-is; decide per-project whether to fix or remove:
- `ui/auth/AuthViewModel.kt` does not follow the MVI contract — it exposes a plain `fun onAuthClick()` instead of `fun onIntent(intent: Intent)`. Fix for consistency before building on it.
- `baselineprofile/` directory is an empty, unwired scaffold — no `build.gradle.kts` and not listed in `settings.gradle.kts`. Either wire it up or delete it.
