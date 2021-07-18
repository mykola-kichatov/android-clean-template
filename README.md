# android-clean-template
Simple Android app, that can be used as a basic structure for general Android native apps.
Here I try to use the best features and practices that I've got from my experience.

# Clean Architecture
There are 3 main modules in the project:
- **domain** is written in pure kotlin, no other dependencies. All business logic should be located here
- **data** module includes all repositories implementations with DB and network layers
- **app** is responsible for presentation. It's an android application module, based on MVVM. It delegates all the business logic to domain layer, staying as passive as possible
