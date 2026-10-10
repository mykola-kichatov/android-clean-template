package com.mkchtv.cleantemplate.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkchtv.cleantemplate.domain.auth.usecase.AuthStateFlow
import com.mkchtv.cleantemplate.domain.auth.usecase.OnAuthenticated
import com.mkchtv.cleantemplate.domain.common.di.AppIoScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class AuthViewModel @Inject constructor(
    @param:AppIoScope private val appIoScope: CoroutineScope,
    authStateFlow: AuthStateFlow,
    private val onAuthenticated: OnAuthenticated,
) : ViewModel() {

    // Kept in the ViewModel, so a gate drawn again, after a rotation or when a gated destination
    // comes back, reads the last value on its first frame and shows no spinner.
    val uiState: StateFlow<UiState> = authStateFlow()
        .map { authState -> UiState(authState = authState) }
        .stateIn(
            initialValue = UiState(),
            scope = viewModelScope,
            started = SharingStarted.Lazily,
        )

    // The write goes on the app scope, so it lands even if the user leaves the screen at once.
    fun onIntent(intent: Intent) = when (intent) {
        Intent.AuthenticateClick -> appIoScope.launch { onAuthenticated() }
    }
}
