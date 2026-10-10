package com.mkchtv.cleantemplate.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
        null -> LoadingScreen()
        AuthState.AUTH_REQUIRED -> AuthScreen(onIntent = viewModel::onIntent)
        AuthState.AUTHENTICATED -> content()
    }
}
