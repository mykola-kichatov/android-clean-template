pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
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
