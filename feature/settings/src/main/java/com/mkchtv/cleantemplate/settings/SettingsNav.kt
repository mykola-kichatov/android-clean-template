package com.mkchtv.cleantemplate.settings

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalSharedTransitionApi
@ExperimentalMaterial3Api
@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
fun NavGraphBuilder.settingsScreen() {
    composable(route = NAV_DESTINATION_SETTINGS) {
        val viewModel = hiltViewModel<SettingsViewModel>()
        SettingsScreen(
            onLogoutConfirmed = { viewModel.onLogoutConfirmed() },
        )
    }
}

const val NAV_DESTINATION_SETTINGS = "settings"

fun NavController.navigateToSettings() = navigate(NAV_DESTINATION_SETTINGS)
