---
name: rebrand
description: Rebrand a fresh clone of android-clean-template into a new app. Asks for the app name, application id, sample-feature and networking decisions, then executes docs/CLONE_CHECKLIST.md end to end. Use on a fresh un-rebranded clone (application id still com.mkchtv.cleantemplate) or when the user asks to set up, rename, or rebrand the template.
---

# Rebrand this template clone

`docs/CLONE_CHECKLIST.md` is the authoritative step list — read it first. This skill only adds the question script and execution order on top; where they disagree, the checklist wins.

## Preflight

`build-logic/convention/src/main/kotlin/Constants.kt` must still contain `APP_ID = "com.mkchtv.cleantemplate"`. If it doesn't, this clone is already rebranded — report that and stop.

## Collect all decisions before editing anything

Free text — ask conversationally in one message:

1. **App name** — the display name. Derive a PascalCase identifier from it (e.g. "My Notes" → `MyNotes`) for `rootProject.name` and the `Theme.<PascalCase>` style; confirm the derivation with the user.
2. **Application id** — e.g. `com.example.mynotes`. Validate: two or more dot-separated segments, each starting with a lowercase letter, only `[a-z0-9_]`.
3. **One-line app description** — used to rewrite `README.md` in the cleanup step.

Choices — one AskUserQuestion call:

4. **Sample `:element` feature** (checklist step 4) — rename into the first real feature (then ask for the domain concept, e.g. `note`) / delete it and start clean.
5. **Networking** (checklist step 5) — set the real API base URL now (then ask for it) / keep the `dummyjson.com` placeholder / remove the Retrofit wiring.
6. **`AuthViewModel` MVI debt** (checklist step 6) — convert to the MVI contract now / leave for later.
7. **`baselineprofile/` scaffold** (checklist step 6) — wire it up / delete it / leave for later.

## Execute

Run checklist steps 1–6 in order with the collected answers. For step 2 use the checklist's no-IDE path: replace `com.mkchtv.cleantemplate` in all files, `git mv` the package directories under each module's `src/main`, `src/test`, and `src/androidTest` roots.

## Verify

Checklist step 7: the stale-reference grep comes back clean, and `./gradlew build` and `./gradlew ktlint` pass. Fix failures before moving on.

## Clean up and report

Checklist step 8: rewrite `README.md` around the one-line description, remove the "Fresh clone?" section from `AGENTS.md` (keep the `CLAUDE.md` pointer file as is), delete `docs/CLONE_CHECKLIST.md`, and delete this skill's directory (`.claude/skills/rebrand/`).

Do not commit — leave the working tree for the user to review. Finish with a summary of what changed and the remaining manual TODOs, at minimum: replace the launcher icons (`app/src/main/res/mipmap-*/`, `app/src/main/res/drawable/ic_launcher_*`), plus real API details if networking was deferred.
