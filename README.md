# android-clean-template
A simple Android app template repository that provides a basic structure for building general Android native applications.
This project incorporates the best practices and features I've gained from my experience.

# Clean Architecture
The project is modularized into the following components:
- [domain](domain) modules are written in pure Kotlin, with no external dependencies. These modules contain use-cases and repository interfaces
- [data](data) modules include all repositories implementations, database, and network layers
- [feature](feature) modules are responsible for presentation. Built using MVVM and Jetpack Compose
- [app](app) is the Android application module that integrates all other modules into a complete app

# Features and technologies
1. Gradle build config is based on Kotlin DSL and [version catalogs](https://developer.android.com/build/migrate-to-catalogs)
2. Dependency injection with [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
3. Jetpack Compose for building UI
4. Declarative conditional navigation
5. Unit tests using JUnit5 and [MockK](https://mockk.io/) 
6. Shared Element Transitions in Jetpack Compose
7. Gradle Convention Plugins for build optimization
8. etc.
