package com.mkchtv.cleantemplate.nav

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.mkchtv.cleantemplate.details.elementDetailsScreen
import com.mkchtv.cleantemplate.details.navigateToElementDetails
import com.mkchtv.cleantemplate.domain.common.Constants
import com.mkchtv.cleantemplate.domain.common.Constants.NEW_ELEMENT_ID
import com.mkchtv.cleantemplate.list.elementListScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalMaterial3Api
@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Constants.NAV_DESTINATION_LIST,
        modifier = modifier
    ) {
        elementListScreen(
            onElementClick = { item -> navController.navigateToElementDetails(item.id) },
            onAddNewElementClick = { navController.navigateToElementDetails(NEW_ELEMENT_ID) }
        )
        elementDetailsScreen(onBackClick = { navController.popBackStack() })
    }
}