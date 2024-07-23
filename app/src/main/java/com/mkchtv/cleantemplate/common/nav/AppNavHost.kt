package com.mkchtv.cleantemplate.common.nav

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.mkchtv.cleantemplate.element.details.elementDetailsScreen
import com.mkchtv.cleantemplate.element.details.navigateToElementDetails
import com.mkchtv.cleantemplate.element.list.NAV_DESTINATION_LIST
import com.mkchtv.cleantemplate.element.list.elementListScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalSharedTransitionApi
@ExperimentalMaterial3Api
@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    SharedTransitionLayout(
        modifier = modifier,
    ) {
        NavHost(
            navController = navController,
            startDestination = NAV_DESTINATION_LIST,
            modifier = Modifier.fillMaxSize(),
        ) {
            elementListScreen(
                sharedTransitionScope = this@SharedTransitionLayout,
                onElementClick = { item -> navController.navigateToElementDetails(item.id) },
                onAddNewElementClick = { navController.navigateToElementDetails() },
            )
            elementDetailsScreen(
                onBackClick = { navController.popBackStack() },
                sharedTransitionScope = this@SharedTransitionLayout,
            )
        }
    }
}
