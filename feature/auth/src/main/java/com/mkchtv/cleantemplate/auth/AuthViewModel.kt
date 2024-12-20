package com.mkchtv.cleantemplate.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkchtv.cleantemplate.domain.auth.usecase.OnAuthenticated
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class AuthViewModel @Inject constructor(
    private val onAuthenticated: OnAuthenticated,
) : ViewModel() {

    fun onAuthClick() = viewModelScope.launch {
        onAuthenticated()
    }
}
