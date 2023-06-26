# android-clean-template
Simple Android app, template repository, that can be used as a basic structure for general Android native apps.
Here I try to use the best features and practices that I've got from my experience.

# Clean Architecture
There are 3 main modules in the project:
- [domain](domain) is written in pure kotlin, no other dependencies. All business logic should be located here
- [data](data) module includes all repositories implementations with DB and network layers
- [app](app) is responsible for presentation. It's an android application module, based on MVVM. It delegates all the business logic to domain layer, staying as passive as possible

# Features and technologies
1. Gradle build config is based on Kotlin DSL and [version catalogs](https://developer.android.com/build/migrate-to-catalogs)
2. Dependency injection with [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
3. Single activity and navigation with [Jetpack Navigation component](https://developer.android.com/guide/navigation/navigation-getting-started)
4. Material design [navigation transitions](https://material.io/design/navigation/navigation-transitions.html)
5. etc.
