# android-clean-template
Simple Android app, template repository, that can be used as a basic structure for general Android native apps.
Here I try to use the best features and practices that I've got from my experience.

# Clean Architecture
There are 3 main modules in the project:
- [domain](domain) is written in pure kotlin, no other dependencies. All business logic should be located here
- [data](data) module includes all repositories implementations with DB and network layers
- [app](app) is responsible for presentation. It's an android application module, based on MVVM and Compose

# Features and technologies
1. Gradle build config is based on Kotlin DSL and [version catalogs](https://developer.android.com/build/migrate-to-catalogs)
2. Dependency injection with [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
3. Jetpack Compose
4. Conditional navigation, using declarative approach
5. [Ktlint Gradle](https://github.com/JLLeitschuh/ktlint-gradle) with [custom rule set](ktlint-rules)
6. Unit tests using JUnit5 and [MockK](https://mockk.io/) 
7. etc.
