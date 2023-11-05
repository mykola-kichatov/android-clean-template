package com.mkchtv.cleantemplate.details

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mkchtv.cleantemplate.auth.AuthProtectedScreen
import com.mkchtv.cleantemplate.domain.common.Constants
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalMaterial3Api
@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
fun NavGraphBuilder.elementDetailsScreen(
    onBackClick: () -> Unit = {}
) {
    composable(
        route = "details/{${Constants.ARG_KEY_ELEMENT_ID}}",
        arguments = listOf(navArgument(Constants.ARG_KEY_ELEMENT_ID) { type = NavType.IntType })
    ) {
        val viewModel = hiltViewModel<ElementDetailsViewModel>()
        val element = viewModel.elementState.collectAsStateWithLifecycle()
        AuthProtectedScreen {
            ElementDetailsScreen(
                element = element.value,
                onBackClick = onBackClick,
                onCreateUpdateConfirmed = { name, desc ->
                    viewModel.onCreateUpdateConfirmed(name = name, description = desc)
                    onBackClick()
                },
                onDeleteConfirmed = {
                    viewModel.onDeleteConfirmed()
                    onBackClick()
                }
            )
        }
    }
}

fun NavController.navigateToElementDetails(elementId: Int) = navigate("details/$elementId")