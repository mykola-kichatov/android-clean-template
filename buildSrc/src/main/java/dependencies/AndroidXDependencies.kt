package dependencies

import constants.Versions

object AndroidXDependencies {

    const val APP_COMPAT = "androidx.appcompat:appcompat:${Versions.APP_COMPAT}"
    const val CORE_KTX = "androidx.core:core-ktx:${Versions.KTX}"
    const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:${Versions.CONSTRAINT_LAYOUT}"
    const val NAVIGATION_FRAGMENT_KTX = "androidx.navigation:navigation-fragment-ktx:${Versions.NAVIGATION_COMPONENT}"
    const val NAVIGATION_UI_KTX = "androidx.navigation:navigation-ui-ktx:${Versions.NAVIGATION_COMPONENT}"
    const val ROOM = "androidx.room:room-ktx:${Versions.ROOM}"
    const val LIFECYCLE_RUNTIME = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.LIFECYCLE}"

    object Compose{
        const val ANIMATION = "androidx.compose.animation:animation:${Versions.COMPOSE}"
        const val ANIMATION_GRAPHICS = "androidx.compose.animation:animation-graphics:${Versions.COMPOSE}"
        const val FOUNDATION = "androidx.compose.foundation:foundation:${Versions.COMPOSE}"
        const val LAYOUT = "androidx.compose.foundation:foundation-layout:${Versions.COMPOSE}"
        const val MATERIAL = "androidx.compose.material:material:${Versions.COMPOSE}"
        const val MATERIAL_ICONS_EXTENDED = "androidx.compose.material:material-icons-extended:${Versions.COMPOSE}"
        const val RUNTIME = "androidx.compose.runtime:runtime:${Versions.COMPOSE}"
        const val TOOLING = "androidx.compose.ui:ui-tooling:${Versions.COMPOSE}"
        const val UI = "androidx.compose.ui:ui:${Versions.COMPOSE}"
        const val UI_UTIL = "androidx.compose.ui:ui-util:${Versions.COMPOSE}"
    }

}