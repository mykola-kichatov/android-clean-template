package com.mkchtv.cleantemplate.nav

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mkchtv.cleantemplate.details.ElementDetailsScreen
import com.mkchtv.cleantemplate.details.ElementDetailsViewModel
import com.mkchtv.cleantemplate.domain.common.Constants
import com.mkchtv.cleantemplate.list.ElementListScreen
import com.mkchtv.cleantemplate.list.ElementsListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = NavDestination.ElementsList.route,
        modifier = modifier
    ) {
        composable(route = NavDestination.ElementsList.route) {
            val viewModel = hiltViewModel<ElementsListViewModel>()
            ElementListScreen(
                viewModel,
                onListItemClick = { item ->
                    val routeWithArgs = NavDestination.ElementDetails.makeRouteWithArgs(item.id)
                    navController.navigate(routeWithArgs)
                },
                onAddNewElementClick = {
                    val routeWithArgs =
                        NavDestination.ElementDetails.makeRouteWithArgs(Constants.NEW_ELEMENT_ID)
                    navController.navigate(routeWithArgs)
                }
            )
        }
        composable(
            route = NavDestination.ElementDetails.route,
            arguments = listOf(navArgument(Constants.ARG_KEY_ELEMENT_ID) { type = NavType.IntType })
        ) {
            val viewModel = hiltViewModel<ElementDetailsViewModel>()
            ElementDetailsScreen(viewModel)
        }
    }
}