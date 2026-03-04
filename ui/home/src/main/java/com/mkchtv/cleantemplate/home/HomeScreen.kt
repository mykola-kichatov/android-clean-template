package com.mkchtv.cleantemplate.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mkchtv.cleantemplate.element.list.navigateToElementList
import com.mkchtv.cleantemplate.home.entity.BottomNavigationItem
import com.mkchtv.cleantemplate.home.entity.BottomNavigationItem.ELEMENT_LIST
import com.mkchtv.cleantemplate.home.entity.BottomNavigationItem.SETTINGS
import com.mkchtv.cleantemplate.settings.navigateToSettings
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalSharedTransitionApi
@ExperimentalMaterial3Api
@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
@Composable
internal fun HomeScreen(
    globalNavController: NavHostController,
) {
    val homeNavController = rememberNavController()
    val navBackStackEntry by homeNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            HomeBottomNavigation(
                selectedItem = BottomNavigationItem.fromRoute(currentRoute),
                onItemClick = { item ->
                    when (item) {
                        ELEMENT_LIST -> homeNavController.navigateToElementList()
                        SETTINGS -> homeNavController.navigateToSettings()
                    }
                },
            )
        },
    ) { innerPadding ->
        HomeNavHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding(),
                )
                .consumeWindowInsets(innerPadding),
            globalNavController = globalNavController,
            homeNavController = homeNavController,
        )
    }
}
