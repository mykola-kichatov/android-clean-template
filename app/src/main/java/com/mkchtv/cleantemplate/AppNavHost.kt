package com.mkchtv.cleantemplate

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.mkchtv.cleantemplate.common.compositionlocal.LocalSharedTransitionScope
import com.mkchtv.cleantemplate.element.details.elementDetailsScreen
import com.mkchtv.cleantemplate.home.NAV_DESTINATION_HOME
import com.mkchtv.cleantemplate.home.homeScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalSharedTransitionApi
@ExperimentalMaterial3Api
@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
@Composable
internal fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) = SharedTransitionLayout(
    modifier = modifier,
) {
    CompositionLocalProvider(LocalSharedTransitionScope provides this) {
        NavHost(
            navController = navController,
            startDestination = NAV_DESTINATION_HOME,
            modifier = Modifier.fillMaxSize(),
        ) {
            homeScreen(
                globalNavController = navController,
            )
            elementDetailsScreen(
                onBackClick = { navController.popBackStack() },
            )
        }
    }
}
