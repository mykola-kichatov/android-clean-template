package com.mkchtv.cleantemplate.data.auth.repository

import com.mkchtv.cleantemplate.domain.auth.entity.AuthState
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class DefaultAuthRepositoryTest {

    @Test
    fun `a fresh start is logged out`() = runTest {
        // [GIVEN]
        val repository = DefaultAuthRepository()

        // [WHEN]
        val authState = repository.authStateFlow.first()

        // [THEN]
        Assertions.assertEquals(AuthState.AUTH_REQUIRED, authState)
    }

    @Test
    fun `logging in logs in`() = runTest {
        // [GIVEN]
        val repository = DefaultAuthRepository()

        // [WHEN]
        repository.onAuthenticated()

        // [THEN]
        Assertions.assertEquals(AuthState.AUTHENTICATED, repository.authStateFlow.first())
    }

    @Test
    fun `logging out logs out again`() = runTest {
        // [GIVEN]
        val repository = DefaultAuthRepository()
        repository.onAuthenticated()

        // [WHEN]
        repository.logout()

        // [THEN]
        Assertions.assertEquals(AuthState.AUTH_REQUIRED, repository.authStateFlow.first())
    }
}
