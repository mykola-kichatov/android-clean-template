package com.mkchtv.cleantemplate.domain.repository

import com.mkchtv.cleantemplate.domain.common.AuthState
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    val authStateFlow: Flow<AuthState>

    suspend fun onAuthenticated()
}