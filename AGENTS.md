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
./gradlew ktlintCheck              # Check code style (build runs it too)
./gradlew ktlintFormat             # Auto-fix code style
./gradlew assemble                 # Build APKs/AARs
```

Example for a specific module: `./gradlew :data:element:test`

## Architecture

Clean Architecture with strict layer separation enforced by module boundaries:

```
domain/* (pure Kotlin, no Android)
    ↑ implemented by
data/* (Room, DataStore, Retrofit, repository impls)
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
- Room for local persistence; Retrofit + kotlinx.serialization for network
- Settings use Preferences DataStore (`:data:settings`), not Room. The file name and every key are permanent: renaming one makes every install silently start from the defaults. Enums are stored by `name`, so reordering them cannot change what an install reads back. The store comes from one top-level `preferencesDataStore` delegate, because DataStore throws if a second instance is ever created on the same file. A corrupt file resets to the defaults, which suits settings and would not suit data nobody can recreate
- Hilt `@Module` with `@Binds` wires interface → implementation at `SingletonComponent` scope
- KSP generates Room and Hilt code
- JSON uses kotlinx.serialization: its compiler plugin writes the serializer of every `@Serializable` class. `NetworkModule.kt` provides one `Json { ignoreUnknownKeys = true }` and Retrofit's converter uses it, so fields a response class does not declare are skipped
- Keep `kotlinxSerialization` in `gradle/libs.versions.toml` on a release built for the catalog's `kotlin` or an older one; the comment there says why

### UI Layer
- `@HiltViewModel` ViewModels inject domain use cases directly
- ViewModels expose `StateFlow` (mapped from domain `Flow`) for Compose to collect
- Use `SharingStarted.Lazily` so the flow survives back-stack navigation without restarting. `MainViewModel` is the one exception, see [MVI contract](#mvi-contract)
- Screens are `@Composable` functions; no business logic in composables

## Dependency Injection

Hilt throughout. Custom coroutine scope qualifiers live in `domain/common/di/Qualifiers.kt`:
- `@AppIoScope` — for IO-bound work
- `@AppDefaultScope` — for CPU-bound work

## Navigation

Two-level Jetpack Navigation Compose:
- `AppNavHost` (app module) — top-level: `HOME` and `ElementDetails` destinations
- `HomeNavHost` (ui/home) — nested bottom-nav tabs: `ElementList`, `Settings`

The tab destinations' `navigateToXxx` extensions accept an optional `NavOptions`; tab switching passes the options built by the private `navigateToTab()` in `HomeScreen.kt` — `popUpTo(startDestination) { saveState = true }` + `launchSingleTop` + `restoreState` — so the back stack holds one entry per tab and tab state survives switching. A new tab follows the same pattern.

Shared element transitions use `SharedTransitionLayout` exposed via `LocalSharedTransitionScope` composition local.

## Theme, splash and previews

`AppTheme(darkTheme, dynamicColor = true, content)` lives in `:ui:common` (`common/theme/`) and wraps every screen. `MainActivity` fills both from the stored settings, which the Settings screen writes: theme mode (system, light or dark) and dynamic color. On Android 12 and newer, dynamic color paints the app from the wallpaper; older versions use the palette in `Color.kt`, and Settings hides the switch. `Type.kt` holds the text styles (the Material 3 defaults) and `Shape.kt` the shapes.

- `Color.kt` sets all 48 Material 3 color roles, light and dark, built from one brand color; its header says how to regenerate them. A role left out of `Theme.kt` silently falls back to Material's purple baseline, so `ColorSchemeTest` fails instead.
- The launch theme in `app/src/main/res/values/themes.xml` builds on the splash screen library (`core-splashscreen`) and hands over to its `.Post` style, which has a `values-night` variant. `MainActivity` calls `installSplashScreen()` before `super.onCreate()`. The launch theme also asks for dark bar icons in light mode, through `@bool/light_system_bars` (`false` in `values-night`): the library's theme leaves them white, and Android 9 draws them white on the light splash.
- Splash hold: `MainActivity` keeps the splash up (`setKeepOnScreenCondition`) until `MainViewModel` has read the stored settings, so the first frame already has the app's theme, not the phone's.
- The status and navigation bar icons follow the app theme, not the phone. `MainActivity` calls `enableEdgeToEdge()` in `onCreate`, then again in a `DisposableEffect(darkTheme)`, with androidx's default scrims and dark mode taken from the setting. Without the second call, an app set to Dark on a light phone draws dark icons on its dark status bar.
- Known limitation: the launch window follows the phone, not the setting. An app set to Dark on a light phone shows a light splash, and its first frame still has the phone's bar icons for a few frames while the system fades them. The fix is AppCompat's `AppCompatDelegate.setDefaultNightMode`, which the template does not use.
- The launch window color, `window_background` in `app/src/main/res/values/colors.xml` and `values-night/colors.xml`, equals the background in `Color.kt`. Keep them in step, or a cold start shows a different color before the first frame.
- The splash icon is `drawable/ic_splash.xml`, the launcher icon's two vector layers stacked. Not `@mipmap/ic_launcher`: below Android 12 the splash library blows its bitmap up and crops the adaptive icon a second time.
- Previews: annotate a screen preview with `@ScreenPreviews` and wrap its body in `ThemedPreview`, both from `:ui:common` (`common/preview/`). `@ScreenPreviews` shows four views: portrait, portrait dark, landscape and landscape dark. `ThemedPreview` switches dynamic color off, so a preview shows the app's own colors on every machine. Reference: `SettingsScreen.kt`.

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

ViewModel tests add `@ExtendWith(MainDispatcherExtension::class)` and check flows with Turbine (`state.test { assertEquals(expected, awaitItem()) }`). Both come from the `:testing` module, which `UnitTestsPlugin` adds to the tests of every module. Without the extension, lifecycle silently runs `viewModelScope` work on a background thread, and the test depends on timing.

A ViewModel that writes on an injected `@AppIoScope` needs that scope unconfined in its test. With `runTest`'s `backgroundScope` alone, the write has not run yet when `coVerify` checks it. Build the ViewModel inside `runTest` and pass this, as `SettingsViewModelTest` does (`UnconfinedTestDispatcher` needs `@OptIn(ExperimentalCoroutinesApi::class)`):

```kotlin
private fun TestScope.appIoScope(): CoroutineScope =
    CoroutineScope(backgroundScope.coroutineContext + UnconfinedTestDispatcher(testScheduler))
```

`UnitTestsPlugin` starts every test JVM with Byte Buddy's agent (`-javaagent`), so MockK never attaches one at runtime. That runtime attach can fail at random under load. When MockK changes version, keep `byteBuddy` in `gradle/libs.versions.toml` equal to the byte-buddy version MockK resolves: `./gradlew :ui:common:dependencies --configuration debugUnitTestRuntimeClasspath | grep byte-buddy`.

## Module Convention Plugins (build-logic)

Each module type uses a convention plugin instead of repeating build config:
- `com.mkchtv.convention.domain` — pure Kotlin/JVM library + unit tests
- `com.mkchtv.convention.data` — Android library + Room (with its Gradle plugin) + Retrofit + kotlinx.serialization + Hilt + unit tests + device-test deps for migration tests
- `com.mkchtv.convention.ui` — Android library + Compose + Hilt + unit tests
- `com.mkchtv.convention.app` — application + all of the above + instrumented tests

`ComposePlugin` gives every Compose module `hilt-lifecycle-viewmodel-compose`, home of `androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel`. Import that one, not the deprecated copy in `androidx.hilt.navigation.compose`.

SDK/version constants are in `build-logic/convention/src/main/kotlin/Constants.kt` (minSdk 24, compileSdk/targetSdk 36, Java 17).

All dependency versions are managed centrally in `gradle/libs.versions.toml`.

ktlint runs through the ktlint-gradle plugin, applied by `DomainPlugin` and by the plugins in `AndroidCommonPlugin.kt`. Keep ktlint-gradle at 14.1.0 or newer. Older versions find the Kotlin code of an Android module only through the `org.jetbrains.kotlin.android` plugin, which AGP 9 built-in Kotlin never applies, so they silently check only the `*.kts` build files. Check: `./gradlew :app:tasks --all` lists `ktlintMainSourceSetCheck`.

## Room schemas and migrations

`DataPlugin` applies the Room Gradle plugin and points it at `<module>/schemas/`. Room exports every database version there as JSON, and those files are committed.

- The schemas are build output. Never edit one by hand. The build restores a deleted one.
- They are the only record of the old table shape, and Room needs them to check a migration.
- A schema folder is named after the database class's full name. Renaming or moving that class means `git mv` of its folder too. The build never removes an old folder.
- Any change to an entity bumps the database version and ships a migration: `@AutoMigration` where Room can derive it, a hand-written `Migration` otherwise.
- `fallbackToDestructiveMigration` is banned in every form. It deletes the user's data.
- A migration ships with a device test on `MigrationTestHelper`, which reads the committed schemas. `DataPlugin` already adds its dependencies (the `dataAndroidTesting` bundle).
- The sample database stays at version 1, with no migration. An app built on the template follows the rules above.

## MVI contract

Each UI feature has a `<Feature>Contract.kt` with up to three types — convention only, no base class:

```kotlin
@Immutable
internal data class UiState(...)       // current screen state; `@Immutable` required for Compose

internal sealed interface Intent { ... } // user-initiated events sent to ViewModel

internal sealed interface Effect { ... } // one-shot effects (navigate, show error)
```

`UiState` and `Intent` are always there. `Effect` only when the screen has one-shot events, such as navigating or showing an error once. A screen without them declares two types, rather than an empty `Effect` and an unused `Channel`.

ViewModel shape:
- `StateFlow<UiState>` built with `.stateIn(SharingStarted.Lazily)` — survives back-stack
- `Channel<Effect>` exposed as `receiveAsFlow()` — one-shot, collected in `LaunchedEffect`
- Single entry point `fun onIntent(intent: Intent)` dispatching a `when` expression

`MainViewModel` in `:app` is the one exception to `Lazily`: it uses `SharingStarted.Eagerly`. The splash is held until its value is read, so nothing may depend on a collector arriving first.

Reference: `ui/element-list/.../ElementListContract.kt` + `ElementListViewModel.kt`. Two types, no `Effect`: `ui/settings/.../SettingsContract.kt` + `SettingsViewModel.kt`

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
<Feature>Contract.kt   — UiState / Intent / Effect if needed (all internal)
<Feature>ViewModel.kt  — @HiltViewModel, internal, holds business logic
<Feature>Screen.kt     — stateless @Composable, no logic
<Feature>Nav.kt        — NavGraphBuilder extension + NavController extension + route/arg constants
```

Sub-package conventions inside a module: `component/`, `di/`, `entity/`, `mapper/`, `usecase/`, `repository/`, `dao/`, `db/`, `network/`, `extension(s)/`, plus `theme/` and `preview/` in `:ui:common`

Visibility rule: ViewModels, Contracts, Repository impls, Room entities/DAOs → `internal`. Domain entities, use cases, repository interfaces → `public`.

Auth-gating: wrap the destination body in `AuthProtectedScreen { ... }` (`:ui:auth`). See `ElementDetailsNav.kt:34`.

## Adding a new feature module

1. Create `domain/<name>/` — convention plugin `com.mkchtv.convention.domain`; add repo interface + use cases
2. Create `data/<name>/` — convention plugin `com.mkchtv.convention.data`; implement repo + Room/Retrofit DI modules
3. Create `ui/<name>-*/` — convention plugin `com.mkchtv.convention.ui`; add Contract + ViewModel + Screen + Nav
4. Register all new modules in `settings.gradle.kts` (follow the existing `include(":domain:element")` lines)
5. Add `implementation(project(":..."))` for each new module in `app/build.gradle.kts`
6. Give every new module a `.gitignore` holding `/build`, like the existing modules. The root `.gitignore` covers only the root build folder, so without it the module's build output lands in `git status`

Reference feature spanning all layers: `:domain:element` → `:data:element` → `:ui:element-list` + `:ui:element-details`

Second reference: `:domain:settings` → `:data:settings` → `:ui:settings`, with Preferences DataStore instead of Room, wired into the theme by `app/.../MainViewModel.kt`. It is not part of the sample and stays when the sample is deleted.

## Known template debt

These two items exist in the template as-is; decide per-project whether to fix or remove:
- `ui/auth/AuthViewModel.kt` does not follow the MVI contract — it exposes a plain `fun onAuthClick()` instead of `fun onIntent(intent: Intent)`. Fix for consistency before building on it.
- `baselineprofile/` directory is an empty, unwired scaffold — no `build.gradle.kts` and not listed in `settings.gradle.kts`. Either wire it up or delete it.
