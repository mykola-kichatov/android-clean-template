package com.mkchtv.cleantemplate.element.details

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mkchtv.cleantemplate.auth.AuthProtectedScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalSharedTransitionApi
@ExperimentalMaterial3Api
@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
fun NavGraphBuilder.elementDetailsScreen(
    onBackClick: () -> Unit = {},
    sharedTransitionScope: SharedTransitionScope,
) {
    composable(
        route = "details/{$ARG_KEY_ELEMENT_ID}",
        arguments = listOf(navArgument(ARG_KEY_ELEMENT_ID) { type = NavType.IntType }),
    ) {
        AuthProtectedScreen {
            val viewModel = hiltViewModel<ElementDetailsViewModel>()
            val screenState = viewModel.screenState.collectAsStateWithLifecycle()
            ElementDetailsScreen(
                screenState = screenState.value,
                onBackClick = onBackClick,
                onCreateConfirmed = { name, desc ->
                    onBackClick()
                    viewModel.onCreateConfirmed(name, desc)
                },
                onUpdateConfirmed = { name, desc, imageUrl ->
                    onBackClick()
                    viewModel.onUpdateConfirmed(name, desc, imageUrl)
                },
                onDeleteConfirmed = {
                    onBackClick()
                    viewModel.onDeleteConfirmed()
                },
                sharedTransitionScope = sharedTransitionScope,
                animatedVisibilityScope = this@composable,
            )
        }
    }
}

fun NavController.navigateToElementDetails(elementId: Int = NEW_ELEMENT_ID) =
    navigate("details/$elementId")

const val ARG_KEY_ELEMENT_ID = "id"
const val NEW_ELEMENT_ID = -1
