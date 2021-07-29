package dependencies

import constants.Versions

object AndroidTestDependencies {

    const val INSTRUMENTATION_RUNNER = "androidx.test.runner.AndroidJUnitRunner"
    const val ANDROIDX_TEST_EXT = "androidx.test.ext:junit:${Versions.ANDROIDX_TEST_EXT}"
    const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:${Versions.ESPRESSO_CORE}"
    const val NAVIGATION_TESTING = "androidx.navigation:navigation-testing:${Versions.NAVIGATION_COMPONENT}"

}