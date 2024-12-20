package com.mkchtv.cleantemplate.domain.auth.usecase

import com.mkchtv.cleantemplate.domain.auth.repository.AuthRepository
import javax.inject.Inject

class GetAuthStateFlow @Inject constructor(
    private val repository: AuthRepository,
) {
    operator fun invoke() = repository.authStateFlow
}
