package com.mkchtv.cleantemplate.domain.auth.repository

import com.mkchtv.cleantemplate.domain.auth.entity.AuthState
import kotlinx.coroutines.flow.Flow

/**
 * Whether the user is logged in. A real login keeps a token in storage, so the state is a [Flow]
 * and the writes suspend, even though the in-memory sample in `:data:auth` never waits.
 */
interface AuthRepository {

    /** Emits the current state, and again on every change. */
    val authStateFlow: Flow<AuthState>

    suspend fun onAuthenticated()

    suspend fun logout()
}
