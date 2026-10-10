---
name: rebrand
description: Rebrand a fresh clone of android-clean-template into a new app. Asks for the app name, application id, and the sample-feature, login and networking decisions, then executes docs/CLONE_CHECKLIST.md end to end. Use on a fresh un-rebranded clone (application id still com.mkchtv.cleantemplate) or when the user asks to set up, rename, or rebrand the template.
---

# Rebrand this template clone

`docs/CLONE_CHECKLIST.md` is the authoritative step list — read it first. This skill only adds the question script and execution order on top; where they disagree, the checklist wins.

## Preflight

`build-logic/convention/src/main/kotlin/Constants.kt` must still contain `APP_ID = "com.mkchtv.cleantemplate"`. If it doesn't, this clone is already rebranded — report that and stop.

## Collect all decisions before editing anything

Free text — ask conversationally in one message:

1. **App name** — the display name. Derive a PascalCase name from it (e.g. "My Notes" → `MyNotes`) for `rootProject.name`; confirm the derivation with the user.
2. **Application id** — e.g. `com.example.mynotes`. Validate: two or more dot-separated segments, each starting with a lowercase letter, only `[a-z0-9_]`. An underscore is fine: checklist step 1 then turns off ktlint's package-name rule. Derive the convention plugin prefix from it, the id without its last segment (`com.example.mynotes` → `com.example`); confirm it with the user.
3. **One-line app description** — used to rewrite `README.md` in the cleanup step.

Choices — one AskUserQuestion call:

4. **Sample `:element` feature** (checklist step 4) — rename into the first real feature (then ask for the domain concept, e.g. `note`) / delete it and start clean. The settings feature is not part of the sample and stays either way.
5. **Fake login** (checklist step 6) — keep it / delete it.

Then a second AskUserQuestion call, with only the questions that apply:

6. **Networking** (checklist step 5) — only if the sample is renamed: set the real API base URL now (then ask for it) / keep the `dummyjson.com` placeholder / remove the Retrofit wiring. With the sample deleted, checklist step 5 is skipped.
7. **`AuthViewModel` MVI debt** (checklist step 6) — only if the login is kept: convert to the MVI contract now / leave for later.

## Execute

Run checklist steps 1–6 in order with the collected answers. For step 2 run the checklist's no-IDE commands, then `git mv` the Room schema folder under `data/element/schemas/` to the new package name. Delete modules and files with `git rm -rf`: step 2 leaves its renames staged, so a plain `git rm` refuses.

## Verify

Checklist step 7: the stale-reference checks of step 2 come back clean, and `./gradlew build` and `./gradlew ktlintCheck` pass. Fix failures before moving on.

## Clean up and report

Checklist step 8: rewrite `README.md` around the one-line description, remove the "Fresh clone?" section from `AGENTS.md` (keep the `CLAUDE.md` pointer file as is), delete `docs/CLONE_CHECKLIST.md`, and delete this skill's directory (`.claude/skills/rebrand/`).

Do not commit — leave the working tree for the user to review. Finish with a summary of what changed and the remaining manual TODOs, at minimum: replace the launcher icons (`app/src/main/res/mipmap-*/`, `app/src/main/res/drawable/ic_launcher_*`; the splash icon follows them), pick the brand color: `#9A4612` is a sample, so regenerate `Color.kt` and the `window_background` colors or keep it (checklist step 3), plus real API details if networking was deferred, plus the picsum placeholder images if the sample feature was renamed: replace or drop them (checklist step 4), plus the fake login if it was kept: replace it with a real login (checklist step 6).
