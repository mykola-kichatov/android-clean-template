package com.mkchtv.cleantemplate.auth

import androidx.lifecycle.ViewModel
import com.mkchtv.cleantemplate.domain.auth.usecase.AuthStateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class AuthProtectedViewModel @Inject constructor(
    authStateFlow: AuthStateFlow,
) : ViewModel() {
    val authStateFlow = authStateFlow()
}
