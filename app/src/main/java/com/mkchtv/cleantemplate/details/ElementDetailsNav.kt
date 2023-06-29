package com.mkchtv.cleantemplate.details

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mkchtv.cleantemplate.domain.common.Constants
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
fun NavGraphBuilder.elementDetailsScreen() {
    composable(
        route = "details/{${Constants.ARG_KEY_ELEMENT_ID}}",
        arguments = listOf(navArgument(Constants.ARG_KEY_ELEMENT_ID) { type = NavType.IntType })
    ) {
        val viewModel = hiltViewModel<ElementDetailsViewModel>()
        val element = viewModel.elementState.collectAsStateWithLifecycle()
        ElementDetailsScreen(element.value)
    }
}

fun NavController.navigateToElementDetails(elementId: Int) = navigate("details/$elementId")