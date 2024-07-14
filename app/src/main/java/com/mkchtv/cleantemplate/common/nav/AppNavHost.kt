package com.mkchtv.cleantemplate.common.nav

import androidx.compose.animation.ExperimentalAnimationApi
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

@ExperimentalMaterial3Api
@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = NAV_DESTINATION_LIST,
        modifier = modifier,
    ) {
        elementListScreen(
            onElementClick = { item -> navController.navigateToElementDetails(item.id) },
            onAddNewElementClick = { navController.navigateToElementDetails() },
        )
        elementDetailsScreen(onBackClick = { navController.popBackStack() })
    }
}
