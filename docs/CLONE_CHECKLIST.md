# Clone checklist

Steps to rebrand a fresh clone of this template. Run them in order: later steps assume the earlier ones are done.

In Claude Code, the `/rebrand` skill runs this checklist interactively — it asks for the decisions, then executes the steps. Using another AI agent? Ask it to run this checklist and interview you for the decisions.

## 1. Rename project

- `settings.gradle.kts:21` — change `rootProject.name = "CleanTemplate"` to your app name
- `build-logic/convention/src/main/kotlin/Constants.kt:7` — change `APP_ID = "com.mkchtv.cleantemplate"` to your application ID. If it has an underscore (`com.my_company.app`), add `ktlint_standard_package-name = disabled` to the `[*.kt]` section of `.editorconfig`. ktlint rejects underscores in package names and cannot fix them, so `./gradlew build` fails on every Kotlin file.
- `build-logic/convention/build.gradle.kts` — change `group = "com.mkchtv.cleantemplate.buildlogic"` to `<your application ID>.buildlogic`. Change the four convention plugin ids `com.mkchtv.convention.*` to a prefix of your own, e.g. `com.example.convention.app` for `com.example.mynotes`. Rename the same four ids in the `[plugins]` section of `gradle/libs.versions.toml`, or Gradle cannot find the plugins, and in `AGENTS.md`.

## 2. Rename package

Use Android Studio's refactor → rename on `com.mkchtv.cleantemplate` to your package. This covers all source roots (domain, data, ui, app, testing).

No IDE (or AI-driven)? Set `NEW` to your package and run this from the project root. It replaces `com.mkchtv.cleantemplate` in every tracked file except this checklist and the rebrand skill, `git mv`s the package folders under every source root (`src/main`, `src/test`, `src/androidTest`) and deletes the empty folders the moves leave behind. Then run the checks below.
```bash
NEW=com.example.mynotes
NEW_DIR=$(echo "$NEW" | tr . /)
git grep -lI 'com\.mkchtv\.cleantemplate' -- ':(exclude)docs/CLONE_CHECKLIST.md' ':(exclude).claude/skills/rebrand' \
  | xargs perl -pi -e "s/com\.mkchtv\.cleantemplate/$NEW/g"
for old in $(find . -type d -path '*/java/com/mkchtv/cleantemplate' -not -path './.git/*'); do
  root=${old%/com/mkchtv/cleantemplate}
  mkdir -p "$root/$(dirname "$NEW_DIR")" && git mv "$old" "$root/$NEW_DIR"
done
find . -depth -type d -empty -path '*/src/*' -delete
```

Either way, also `git mv` the Room schema folder `data/element/schemas/com.mkchtv.cleantemplate.data.element.db.AppDatabase/` to the new package name. Room names the folder after the database class's full name, and the build never removes an old one.

Verify no stale references remain:
```bash
grep -rlE "mkchtv|cleantemplate" --include="*.kt" --include="*.xml" --include="*.kts" --include="*.toml" --exclude-dir=build --exclude-dir=.gradle .
find . -type d \( -name "*mkchtv*" -o -name "*cleantemplate*" \) -not -path "*/build/*"
```

## 3. Rename app resources

- `app/src/main/res/values/strings.xml` — change `app_name`. The launch theme styles, `Theme.Main` and `Theme.Main.Post`, have neutral names and need no rename.
- Brand color — `#9A4612` is only a sample. To use yours, regenerate all 48 color roles in `ui/common/src/main/java/.../common/theme/Color.kt` (its header says how), then set `window_background` in `app/src/main/res/values/colors.xml` and `app/src/main/res/values-night/colors.xml` to the new light and dark `background`. `ColorSchemeTest` fails if a role is missing. To keep the sample palette, change nothing. The `AppTheme` composable in `Theme.kt` needs no rename either way.
- Replace launcher icons: `app/src/main/res/mipmap-*/` and `app/src/main/res/drawable/ic_launcher_*`. The splash icon, `app/src/main/res/drawable/ic_splash.xml`, stacks the two `ic_launcher_*` layers, so it follows.

## 4. Decide on the sample `:element` feature

**Option A — rename it as your first real feature:**
Rename `:domain:element`, `:data:element`, `:ui:element-list`, `:ui:element-details` to your domain concept. Update their `include()` lines in `settings.gradle.kts` and every `implementation(project(...))` line that names them: in `app/build.gradle.kts`, `ui/home/build.gradle.kts` and the four modules' own build files. Then update the sample's names in `AGENTS.md` (search it for `element`).

The random images are a sample too. `data/element/.../repository/PicsumImageUrlProvider.kt` returns placeholder images from picsum.photos. It is here to show a domain interface (`ImageUrlProvider`) with its implementation in the data layer. Point it at your own image source, or drop images from your feature. To drop them, delete `ImageUrlProvider`, `PicsumImageUrlProvider`, its `@Binds` in `RepositoryModule.kt` and `GetRandomImageUrl`, then remove the `imageUrl` usages the compiler shows you.

**Option B — delete it and start clean:**
Remove the four modules, their `include()` lines from `settings.gradle.kts`, and their `implementation(project(...))` lines from `app/build.gradle.kts` and `ui/home/build.gradle.kts`. Remove the element destination from `AppNavHost.kt`. Remove the list tab from `ui/home`: `HomeNavHost.kt` (settings becomes the start destination), `HomeScreen.kt`, `BottomNavigationItem.kt`, `BottomNavigation.kt` and the `cd_bottom_bar_list` string. Coil loads only the sample's images, so also remove the Coil lines from `gradle/libs.versions.toml`. `AGENTS.md` uses the sample as its reference code (search it for `element`): point each reference at the settings feature where it shows the same thing, and drop the rest. Keep the rules those references illustrate, such as the "Error handling" section: they hold for your own features.

Either way, the settings feature (`:domain:settings`, `:data:settings`, `:ui:settings`) is not part of the sample. It stays: the theme reads its stored values.

## 5. Point at your real API

- `data/element/src/main/java/.../data/element/di/NetworkModule.kt` — change the `BASE_URL` constant (`"https://dummyjson.com/"`) to your API base URL, or remove the entire Retrofit wiring if you don't need networking yet.

With Option B this file went with `:data:element`, so skip this step. `DataPlugin` still gives every data module Retrofit and kotlinx.serialization, ready for your own API.

## 6. Decide on the fake login

The `:auth` feature is only a sample: an in-memory login with one "Authenticate" button. It shows how to gate screens behind a login. Keep it until your real login replaces it, or delete it.

**Option A — keep it.** Nothing to do now. When your real login comes, it replaces the in-memory `DefaultAuthRepository` in `:data:auth` and the one-button `AuthScreen.kt` in `:ui:auth`; the gate stays.

**Option B — delete it:**
- Remove the modules `:domain:auth`, `:data:auth` and `:ui:auth`, their `include()` lines from `settings.gradle.kts` and their `implementation(project(...))` lines from `app/build.gradle.kts`.
- Remove the gate. In `app/src/main/java/.../AppNavHost.kt`, `AppNavHost` is `= AuthProtectedScreen { ... }`: make it a block body `{ ... }` with the same content, delete the comment about the gate inside it, and drop the import.
- Remove logout from `ui/settings`: the `:domain:auth` line in its `build.gradle.kts`; in `SettingsViewModel.kt`, the `Logout` parameter and the `Intent.LogoutConfirmed` branch of `onIntent`; `Intent.LogoutConfirmed` and the logout sentence of the comment in `SettingsContract.kt`; in `SettingsScreen.kt`, the top-bar button with `TopBar`'s `onLogoutRequested` parameter, and the confirm dialog with its `showConfirmLogoutDialog` state; the strings `confirm_logout` and `cd_logout`, with the comment above `cd_logout`; in `SettingsViewModelTest.kt`, the `Logout` mock, the `logout = mockLogout` argument and the logout test. Then delete the imports this leaves unused: ktlint fails the build on them.
- In `AGENTS.md`, delete the "Auth-gating" line, and `, behind the login gate (see Auth-gating)` at the end of the `AppNavHost` line in the Navigation section.

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
