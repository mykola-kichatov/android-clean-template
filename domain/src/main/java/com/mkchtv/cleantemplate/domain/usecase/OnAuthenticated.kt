package com.mkchtv.cleantemplate.domain.usecase

import com.mkchtv.cleantemplate.domain.repository.AuthRepository
import javax.inject.Inject

interface OnAuthenticatedUseCase {
    suspend operator fun invoke()
}

class OnAuthenticated @Inject constructor(
    private val repository: AuthRepository
) : OnAuthenticatedUseCase {
    override suspend operator fun invoke() = repository.onAuthenticated()
}