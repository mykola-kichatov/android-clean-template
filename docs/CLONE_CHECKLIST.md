# Clone checklist

Steps to rebrand a fresh clone of this template. Run them in order — each step is independent but later steps assume the previous ones are done.

In Claude Code, the `/rebrand` skill runs this checklist interactively — it asks for the decisions, then executes the steps. Using another AI agent? Ask it to run this checklist and interview you for the decisions.

## 1. Rename project

- `settings.gradle.kts:21` — change `rootProject.name = "CleanTemplate"` to your app name
- `build-logic/convention/src/main/kotlin/Constants.kt:7` — change `APP_ID = "com.mkchtv.cleantemplate"` to your application ID

## 2. Rename package

Use Android Studio's refactor → rename on `com.mkchtv.cleantemplate` to your package. This covers all source roots (domain, data, ui, app, testing).

No IDE (or AI-driven)? Replace `com.mkchtv.cleantemplate` in all files and `git mv` the package directories under each module's source roots (`src/main`, `src/test`, `src/androidTest`), then run the checks below.

Either way, also `git mv` the Room schema folder `data/element/schemas/com.mkchtv.cleantemplate.data.element.db.AppDatabase/` to the new package name. Room names the folder after the database class's full name, and the build never removes an old one.

Verify no stale references remain:
```bash
grep -r "mkchtv\|cleantemplate" --include="*.kt" --include="*.xml" --include="*.kts" -l
find . -type d \( -name "*mkchtv*" -o -name "*cleantemplate*" \) -not -path "*/build/*"
```

## 3. Rename app resources

- `app/src/main/res/values/strings.xml` — change `app_name`
- Rename the styles `Theme.CleanTemplate` to `Theme.<YourApp>` and `Theme.CleanTemplate.Post` to `Theme.<YourApp>.Post`: both in `app/src/main/res/values/themes.xml` (with the `postSplashScreenTheme` reference), `.Post` in `app/src/main/res/values-night/themes.xml`, and the `android:theme` reference in `app/src/main/AndroidManifest.xml`. The grep of step 2 does not find them, it is case-sensitive.
- Brand color — `#9A4612` is only a sample. To use yours, regenerate all 48 color roles in `ui/common/src/main/java/.../common/theme/Color.kt` (its header says how), then set `window_background` in `app/src/main/res/values/colors.xml` and `app/src/main/res/values-night/colors.xml` to the new light and dark `background`. `ColorSchemeTest` fails if a role is missing. To keep the sample palette, change nothing. The `AppTheme` composable in `Theme.kt` needs no rename either way.
- Replace launcher icons: `app/src/main/res/mipmap-*/` and `app/src/main/res/drawable/ic_launcher_*`. The splash icon, `app/src/main/res/drawable/ic_splash.xml`, stacks the two `ic_launcher_*` layers, so it follows.

## 4. Decide on the sample `:element` feature

**Option A — rename it as your first real feature:**
Rename `:domain:element`, `:data:element`, `:ui:element-list`, `:ui:element-details` to your domain concept. Update their `include()` lines in `settings.gradle.kts` and their `implementation(project(...))` lines in `app/build.gradle.kts`.

The random images are a sample too. `data/element/.../repository/PicsumImageUrlProvider.kt` returns placeholder images from picsum.photos. It is here to show a domain interface (`ImageUrlProvider`) with its implementation in the data layer. Point it at your own image source, or drop images from your feature. To drop them, delete `ImageUrlProvider`, `PicsumImageUrlProvider`, its `@Binds` in `RepositoryModule.kt` and `GetRandomImageUrl`, then remove the `imageUrl` usages the compiler shows you.

**Option B — delete it and start clean:**
Remove the four modules, remove their `include()` lines from `settings.gradle.kts`, remove their `implementation(project(...))` lines from `app/build.gradle.kts`. Also remove the `AppNavHost` references to element destinations and the `HomeNavHost` bottom-nav tab.

Either way, the settings feature (`:domain:settings`, `:data:settings`, `:ui:settings`) is not part of the sample. It stays: the theme reads its stored values.

## 5. Point at your real API

- `data/element/src/main/java/.../data/element/di/NetworkModule.kt` — change the `BASE_URL` constant (`"https://dummyjson.com/"`) to your API base URL, or remove the entire Retrofit wiring if you don't need networking yet.

## 6. Fix template debt (recommended before building on top)

- `ui/auth/AuthViewModel.kt` — convert to MVI contract (add `Intent` sealed interface, rename `onAuthClick()` to `onIntent(intent: Intent)`) for consistency with all other ViewModels.
- `baselineprofile/` — either wire it up (add `build.gradle.kts`, add `include(":baselineprofile")` to `settings.gradle.kts`) or delete the directory.

## 7. Verify the build

```bash
./gradlew build
./gradlew ktlintCheck
```

## 8. Clean up template scaffolding

- Rewrite `README.md` to describe your app (it still describes the template)
- Remove the "Fresh clone?" section from `AGENTS.md`
- Delete `docs/CLONE_CHECKLIST.md`
- Delete the rebrand skill: `.claude/skills/rebrand/`
