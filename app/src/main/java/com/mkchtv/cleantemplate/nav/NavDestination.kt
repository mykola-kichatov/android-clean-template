package com.mkchtv.cleantemplate.nav

import com.mkchtv.cleantemplate.domain.common.Constants

sealed interface NavDestination {
    val route: String

    object ElementsList : NavDestination {
        override val route = "list"
    }

    object ElementDetails : NavDestination {
        override val route = "details/{${Constants.ARG_KEY_ELEMENT_ID}}"
        fun makeRouteWithArgs(elementId: Int) = "details/$elementId"
    }
}
