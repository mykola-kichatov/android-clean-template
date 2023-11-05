package com.mkchtv.cleantemplate.domain.usecase

import com.mkchtv.cleantemplate.domain.common.AuthState
import com.mkchtv.cleantemplate.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetAuthStateFlowUseCase {
    operator fun invoke(): Flow<AuthState>
}

class GetAuthStateFlow @Inject constructor(
    private val repository: AuthRepository
) : GetAuthStateFlowUseCase {
    override operator fun invoke() = repository.authStateFlow
}