pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "CleanTemplate"

include(":app")

include(":data:auth")
include(":data:element")

include(":domain:auth")
include(":domain:common")
include(":domain:element")

include(":feature:auth")
include(":feature:common")
include(":feature:element-details")
include(":feature:element-list")
include(":feature:home")
include(":feature:settings")
