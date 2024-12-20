package com.mkchtv.cleantemplate.domain.auth.repository

import com.mkchtv.cleantemplate.domain.auth.entity.AuthState
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    val authStateFlow: Flow<AuthState>

    suspend fun onAuthenticated()
}
