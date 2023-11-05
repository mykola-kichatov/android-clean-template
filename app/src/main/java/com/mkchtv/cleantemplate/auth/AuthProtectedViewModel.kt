package com.mkchtv.cleantemplate.auth

import androidx.lifecycle.ViewModel
import com.mkchtv.cleantemplate.domain.usecase.GetAuthStateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthProtectedViewModel @Inject constructor(
    getAuthStateFlow: GetAuthStateFlow,
) : ViewModel() {
    val authStateFlow = getAuthStateFlow()
}