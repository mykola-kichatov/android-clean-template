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
- A use case reports a failure by throwing, never by returning `Result`. See [Error handling](#error-handling)
- No Android or external dependencies — only `javax.inject` and coroutines

### Data Layer
- Implements domain repository interfaces (`DefaultXxxRepository : XxxRepository`)
- Room for local persistence; Retrofit + kotlinx.serialization for network
- A DAO insert that creates rows is a plain `@Insert`, never `OnConflictStrategy.REPLACE`: on a conflict REPLACE deletes the existing row, and with foreign keys its child rows too. Data that may already exist, such as a server sync, uses `@Upsert`. A query that returns a list states its `ORDER BY`; without one, SQL promises no order
- Settings use Preferences DataStore (`:data:settings`), not Room. The file name and every key are permanent: renaming one makes every install silently start from the defaults. Enums are stored by `name`, so reordering them cannot change what an install reads back. The store comes from one top-level `preferencesDataStore` delegate, because DataStore throws if a second instance is ever created on the same file. A corrupt file resets to the defaults, which suits settings and would not suit data nobody can recreate
- Auto Backup saves everything the app stores, to the user's Google account and to a new phone. The rules are `app/src/main/res/xml/data_extraction_rules.xml` (Android 12 and newer) and `backup_rules.xml` (older); keep the two in step. Exclude what must not come back on another phone, such as a login token or data encrypted with an Android Keystore key, which the new phone cannot decrypt
- Hilt `@Module` with `@Binds` wires interface → implementation at `SingletonComponent` scope
- KSP generates Room and Hilt code
- JSON uses kotlinx.serialization: its compiler plugin writes the serializer of every `@Serializable` class. `NetworkModule.kt` provides one `Json { ignoreUnknownKeys = true }` and Retrofit's converter uses it, so fields a response class does not declare are skipped
- Keep `kotlinxSerialization` in `gradle/libs.versions.toml` on a release built for the catalog's `kotlin` or an older one; the comment there says why

### UI Layer
- `@HiltViewModel` ViewModels inject domain use cases directly
- ViewModels expose `StateFlow` (mapped from domain `Flow`) for Compose to collect
- Use `SharingStarted.Lazily` so the flow survives back-stack navigation without restarting. `MainViewModel` is the one exception, see [MVI contract](#mvi-contract)
- Screens are `@Composable` functions; no business logic in composables
- State that only the screen needs, such as whether a dialog is open, stays in the composable in `rememberSaveable`, so it survives rotation and process death. `remember` loses it in both cases

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

Predictive back is on: `android:enableOnBackInvokedCallback="true"` in the app manifest. Android 13–15 need it; Android 16 turns it on by default for apps that target API 36. During a back swipe, Navigation plays the pop transition with the finger. Handle Back with Navigation or `BackHandler`/`PredictiveBackHandler`, never by overriding `onBackPressed()`, which predictive back never calls.

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

A test that makes such a write fail needs the scope with its own `SupervisorJob`, like the real one. On the bare `backgroundScope` job, `runTest` reports the failed write as uncaught and the test fails, though the ViewModel caught the error from `await`. `ElementDetailsViewModelTest` does this:

```kotlin
private fun TestScope.appIoScope(): CoroutineScope = CoroutineScope(
    backgroundScope.coroutineContext +
        SupervisorJob(backgroundScope.coroutineContext.job) +
        UnconfinedTestDispatcher(testScheduler),
)
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
- `@AppIoScope` (injected `CoroutineScope`) — for writes (DB/network) that must survive ViewModel death; uses `SupervisorJob + Dispatchers.IO`

```kotlin
// fire-and-forget write: appIoScope so it outlives the ViewModel
appIoScope.launch { setThemeMode(themeMode) }
// a write the screen waits for: still on appIoScope, awaited from viewModelScope
viewModelScope.launch {
    appIoScope.async { deleteElement(elementId) }.await()
    _effects.send(Effect.NavigateBack) // cancels if the screen is gone
}
```

Reference: `ui/settings/.../SettingsViewModel.kt` (fire-and-forget), `ui/element-details/.../ElementDetailsViewModel.kt` (`closeAfter` waits, with the error handling below)

Note: the `@AppIoScope` / `@AppDefaultScope` qualifiers are defined in `domain/common/di/Qualifiers.kt` but the actual `CoroutineScope` bindings are provided in `ui/common/di/CoroutinesModule.kt`.

## Error handling

Use cases and repositories report a failure by throwing. No `Result` or `runCatching` wrappers: `runCatching` around a suspend call also catches `CancellationException`.

The ViewModel catches where it calls the use case, and lets cancellation pass first. Cancellation is not an error: the user left, and the coroutine must stop.

```kotlin
try {
    pullElement()
} catch (e: CancellationException) {
    throw e
} catch (e: Exception) {
    _effects.send(ShowError(e.toMessageRes()))
}
```

- The user sees a string resource, never `Throwable.message`: that text is technical, English only and can be null. `Effect.ShowError` carries a `@StringRes Int`, picked by `toMessageRes()` in `:ui:common` (`common/extensions/`): an `IOException` gets the no-connection message, anything else the generic one. Here only the network throws `IOException`; DataStore throws one too, for a broken file, so a feature that writes one needs its own message. An HTTP error status gets the generic one. A project that needs more maps it in the data layer, so ui modules stay free of Retrofit.
- A screen that saves waits for the write, and closes only when it worked. The write runs on `@AppIoScope` with `async`, awaited from `viewModelScope`: it finishes even if the user leaves, and a failure comes back to the screen, which stays open with the user's input and shows the error. Not `launch` + `join`: a failed `launch` goes to the scope's exception handler, never to the caller.
- While the write runs, `UiState.isSaving` disables the save actions, and the ViewModel ignores a second save, because the keyboard can submit too. Without it, a double tap saved twice and closed twice.
- The sample's Room flows are not caught: a failing local query is a bug, and the crash shows it. A flow that can fail for an expected reason (network, file) gets `.catch` before `stateIn` and turns the error into state.

Reference: `ElementListViewModel.kt` (pull), `ElementDetailsViewModel.kt` (`closeAfter`)

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

Auth-gating: wrap the destination body in `AuthProtectedScreen { ... }` (`:ui:auth`). See `ElementDetailsNav.kt`. `:ui:auth` is not a destination, so it has no Nav file: `AuthProtectedScreen` connects `AuthViewModel` to `AuthScreen`.

## Adding a new feature module

1. Create `domain/<name>/` — convention plugin `com.mkchtv.convention.domain`; add repo interface + use cases
2. Create `data/<name>/` — convention plugin `com.mkchtv.convention.data`; implement repo + Room/Retrofit DI modules
3. Create `ui/<name>-*/` — convention plugin `com.mkchtv.convention.ui`; add Contract + ViewModel + Screen + Nav
4. Register all new modules in `settings.gradle.kts` (follow the existing `include(":domain:element")` lines)
5. Add `implementation(project(":..."))` for each new module in `app/build.gradle.kts`
6. Give every new module a `.gitignore` holding `/build`, like the existing modules. The root `.gitignore` covers only the root build folder, so without it the module's build output lands in `git status`

Reference feature spanning all layers: `:domain:element` → `:data:element` → `:ui:element-list` + `:ui:element-details`

Second reference: `:domain:settings` → `:data:settings` → `:ui:settings`, with Preferences DataStore instead of Room, wired into the theme by `app/.../MainViewModel.kt`. It is not part of the sample and stays when the sample is deleted.
