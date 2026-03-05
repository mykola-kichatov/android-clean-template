# android-clean-template
A multi-module Android starter template using Clean Architecture, Kotlin, Jetpack Compose, MVI, and Hilt — ready to clone and build on.

![Min SDK](https://img.shields.io/badge/Min%20SDK-24-3DDC84?logo=android&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-Latest-02303A?logo=gradle&logoColor=white)
![Libraries](https://img.shields.io/badge/Libraries-Latest-7F52FF?logo=kotlin&logoColor=white)

# Clean Architecture
The project is modularized following the repository pattern with strict layer separation:
- [domain](domain) — pure Kotlin modules; contains use cases and repository interfaces, no Android dependencies
- [data](data) — repository implementations, Room database, Retrofit + Moshi network layer
- [ui](ui) — presentation layer built with MVVM/MVI and Jetpack Compose; ViewModels expose StateFlow/Flow
- [app](app) — application module; Hilt entry point, navigation host, wires all modules together

# Features and technologies
1. Gradle build configuration using Kotlin DSL, Version Catalogs, and Convention Plugins
2. Dependency injection with [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
3. Jetpack Compose UI with Material 3
4. Kotlin Coroutines and Flow for async/reactive data streams
5. Room for local persistence, Retrofit + Moshi for networking
6. Declarative conditional navigation with shared element transitions
7. Unit tests with JUnit5 and [MockK](https://mockk.io/)
8. Centralized [build configuration](build-logic) with Gradle Convention Plugins

# Getting Started
1. Click **Use this template** (or `git clone` this repo)
2. Open in Android Studio
3. Sync Gradle, then run on a device or emulator (min API 24)
