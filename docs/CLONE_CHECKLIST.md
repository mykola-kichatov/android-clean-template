# Clone checklist

Steps to rebrand a fresh clone of this template. Run them in order — each step is independent but later steps assume the previous ones are done.

In Claude Code, the `/rebrand` skill runs this checklist interactively — it asks for the decisions, then executes the steps. Using another AI agent? Ask it to run this checklist and interview you for the decisions.

## 1. Rename project

- `settings.gradle.kts:21` — change `rootProject.name = "CleanTemplate"` to your app name
- `build-logic/convention/src/main/kotlin/Constants.kt:7` — change `APP_ID = "com.mkchtv.cleantemplate"` to your application ID

## 2. Rename package

Use Android Studio's refactor → rename on `com.mkchtv.cleantemplate` to your package. This covers all source roots (domain, data, ui, app).

No IDE (or AI-driven)? Replace `com.mkchtv.cleantemplate` in all files and `git mv` the package directories under each module's source roots (`src/main`, `src/test`, `src/androidTest`), then run the grep below.

Verify no stale references remain:
```bash
grep -r "mkchtv\|cleantemplate" --include="*.kt" --include="*.xml" --include="*.kts" -l
```

## 3. Rename app resources

- `app/src/main/res/values/strings.xml` — change `app_name`
- `app/src/main/res/values/themes.xml:3` — rename style `Theme.CleanTemplate` to `Theme.<YourApp>`, and update the reference in `app/src/main/AndroidManifest.xml:13`
- `app/src/main/java/com/mkchtv/cleantemplate/theme/` — update the color palette in `Color.kt` (the `AppTheme` composable in `Theme.kt` needs no rename)
- Replace launcher icons: `app/src/main/res/mipmap-*/` and `app/src/main/res/drawable/ic_launcher_*`

## 4. Decide on the sample `:element` feature

**Option A — rename it as your first real feature:**
Rename `:domain:element`, `:data:element`, `:ui:element-list`, `:ui:element-details` to your domain concept. Update `settings.gradle.kts:26,30,34,35` and `app/build.gradle.kts` accordingly.

**Option B — delete it and start clean:**
Remove the four modules, remove their `include()` lines from `settings.gradle.kts`, remove their `implementation(project(...))` lines from `app/build.gradle.kts`. Also remove the `AppNavHost` references to element destinations and the `HomeNavHost` bottom-nav tab.

## 5. Point at your real API

- `data/element/src/main/java/.../data/element/di/NetworkModule.kt:18` — change `BASE_URL = "https://dummyjson.com/"` to your API base URL, or remove the entire Retrofit wiring if you don't need networking yet.

## 6. Fix template debt (recommended before building on top)

- `ui/auth/AuthViewModel.kt` — convert to MVI contract (add `Intent` sealed interface, rename `onAuthClick()` to `onIntent(intent: Intent)`) for consistency with all other ViewModels.
- `baselineprofile/` — either wire it up (add `build.gradle.kts`, add `include(":baselineprofile")` to `settings.gradle.kts`) or delete the directory.

## 7. Verify the build

```bash
./gradlew build
./gradlew ktlint
```

## 8. Clean up template scaffolding

- Rewrite `README.md` to describe your app (it still describes the template)
- Remove the "Fresh clone?" section from `AGENTS.md`
- Delete `docs/CLONE_CHECKLIST.md`
- Delete the rebrand skill: `.claude/skills/rebrand/`
