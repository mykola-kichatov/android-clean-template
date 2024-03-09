package com.mkchtv.cleantemplate.domain.usecase

import com.mkchtv.cleantemplate.domain.repository.AuthRepository
import javax.inject.Inject

class OnAuthenticated @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke() = repository.onAuthenticated()
}
