package dependencies

import constants.Versions

object BuildDependencies {

    const val GRADLE_BUILD_TOOLS = "com.android.tools.build:gradle:${Versions.GRADLE_BUILD_TOOLS}"
    const val KOTLIN_GRADLE_PLUGIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN}"
    const val HILT_GRADLE_PLUGIN = "com.google.dagger:hilt-android-gradle-plugin:${Versions.HILT}"
    const val NAVIGATION_SAFE_ARGS = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.NAVIGATION_COMPONENT}"

}