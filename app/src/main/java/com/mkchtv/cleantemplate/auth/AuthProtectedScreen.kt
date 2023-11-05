package com.mkchtv.cleantemplate.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.mkchtv.cleantemplate.common.LoadingScreen
import com.mkchtv.cleantemplate.domain.common.AuthState

@Composable
fun AuthProtectedScreen(
    viewModel: AuthProtectedViewModel = hiltViewModel(),
    content: @Composable () -> Unit,
) {
    val authState by viewModel.authStateFlow.collectAsState(AuthState.LOADING)

    when (authState) {
        AuthState.LOADING -> LoadingScreen()
        AuthState.AUTH_REQUIRED -> AuthScreen()
        AuthState.AUTHENTICATED -> content()
    }
}