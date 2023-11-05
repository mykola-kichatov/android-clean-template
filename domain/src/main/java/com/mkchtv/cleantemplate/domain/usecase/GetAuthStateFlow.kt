package com.mkchtv.cleantemplate.domain.usecase

import com.mkchtv.cleantemplate.domain.repository.AuthRepository
import javax.inject.Inject

class GetAuthStateFlow @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke() = repository.authStateFlow
}