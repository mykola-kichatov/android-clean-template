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

include(":ui:auth")
include(":ui:common")
include(":ui:element-details")
include(":ui:element-list")
include(":ui:home")
include(":ui:settings")
