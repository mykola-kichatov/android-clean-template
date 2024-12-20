package com.mkchtv.cleantemplate.domain.auth.usecase

import com.mkchtv.cleantemplate.domain.auth.repository.AuthRepository
import javax.inject.Inject

class OnAuthenticated @Inject constructor(
    private val repository: AuthRepository,
) {
    suspend operator fun invoke() = repository.onAuthenticated()
}
