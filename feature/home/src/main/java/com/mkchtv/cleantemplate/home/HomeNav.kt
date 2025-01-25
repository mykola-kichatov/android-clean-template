package com.mkchtv.cleantemplate.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.mkchtv.cleantemplate.auth.AuthProtectedScreen
import com.mkchtv.cleantemplate.common.compositionlocal.LocalNavAnimatedVisibilityScope
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalSharedTransitionApi
@ExperimentalMaterial3Api
@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
fun NavGraphBuilder.homeScreen(
    globalNavController: NavHostController,
) {
    composable(route = NAV_DESTINATION_HOME) {
        CompositionLocalProvider(
            LocalNavAnimatedVisibilityScope provides this@composable
        ) {
            AuthProtectedScreen {
                HomeScreen(
                    globalNavController = globalNavController,
                )
            }
        }
    }
}

const val NAV_DESTINATION_HOME = "home"
