package com.mkchtv.cleantemplate.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.mkchtv.cleantemplate.element.details.navigateToElementDetails
import com.mkchtv.cleantemplate.element.list.NAV_DESTINATION_LIST
import com.mkchtv.cleantemplate.element.list.elementListScreen
import com.mkchtv.cleantemplate.settings.settingsScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalSharedTransitionApi
@ExperimentalMaterial3Api
@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
@Composable
internal fun HomeNavHost(
    globalNavController: NavHostController,
    homeNavController: NavHostController,
    modifier: Modifier = Modifier,
) = NavHost(
    navController = homeNavController,
    startDestination = NAV_DESTINATION_LIST,
    modifier = modifier,
) {
    elementListScreen(
        onElementClick = { itemId -> globalNavController.navigateToElementDetails(itemId) },
        onAddNewElementClick = { globalNavController.navigateToElementDetails() },
    )
    settingsScreen()
}
