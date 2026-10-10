package com.mkchtv.cleantemplate.auth

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mkchtv.cleantemplate.common.component.HoldSplashScreen
import com.mkchtv.cleantemplate.common.component.LoadingScreen
import com.mkchtv.cleantemplate.domain.auth.entity.AuthState

@Composable
fun AuthProtectedScreen(
    content: @Composable () -> Unit,
) = Screen(content = content)

@Composable
private fun Screen(
    viewModel: AuthViewModel = hiltViewModel(),
    content: @Composable () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState.authState) {
        null -> {
            // At a cold start the splash stays up until the login state is read, so the app
            // never shows this spinner right after it.
            HoldSplashScreen()
            LoadingScreen(Modifier.fillMaxSize())
        }
        AuthState.AUTH_REQUIRED -> AuthScreen(onIntent = viewModel::onIntent)
        AuthState.AUTHENTICATED -> content()
    }
}
