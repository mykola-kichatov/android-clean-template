package com.mkchtv.cleantemplate.element.details

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mkchtv.cleantemplate.auth.AuthProtectedScreen
import com.mkchtv.cleantemplate.common.compositionlocal.LocalAnimatedVisibilityScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest

@ExperimentalSharedTransitionApi
@ExperimentalMaterial3Api
@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
fun NavGraphBuilder.elementDetailsScreen(
    onBackClick: () -> Unit = {},
) {
    composable(
        route = "details/{$ARG_KEY_ELEMENT_ID}",
        arguments = listOf(navArgument(ARG_KEY_ELEMENT_ID) { type = NavType.IntType }),
    ) {
        CompositionLocalProvider(
            LocalAnimatedVisibilityScope provides this@composable
        ) {
            AuthProtectedScreen {
                val viewModel = hiltViewModel<ElementDetailsViewModel>()
                val uiState = viewModel.uiState.collectAsStateWithLifecycle()

                LaunchedEffect(viewModel) {
                    viewModel.effects.collectLatest { effect ->
                        when (effect) {
                            Effect.NavigateBack -> onBackClick()
                        }
                    }
                }

                ElementDetailsScreen(
                    uiState = uiState.value,
                    onIntent = viewModel::onIntent,
                )
            }
        }
    }
}

fun NavController.navigateToElementDetails(elementId: Int = NEW_ELEMENT_ID) =
    navigate("details/$elementId")

const val ARG_KEY_ELEMENT_ID = "id"
const val NEW_ELEMENT_ID = -1
