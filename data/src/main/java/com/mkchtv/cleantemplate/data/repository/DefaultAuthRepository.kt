package com.mkchtv.cleantemplate.data.repository

import com.mkchtv.cleantemplate.domain.common.AuthState
import com.mkchtv.cleantemplate.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultAuthRepository @Inject constructor(

) : AuthRepository {

    private val _authStateFlow = MutableStateFlow(AuthState.AUTH_REQUIRED)
    override val authStateFlow: Flow<AuthState> = _authStateFlow

    override suspend fun onAuthenticated() {
        _authStateFlow.value = AuthState.AUTHENTICATED
    }
}