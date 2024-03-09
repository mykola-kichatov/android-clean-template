package com.mkchtv.cleantemplate.domain.usecase

import com.mkchtv.cleantemplate.domain.repository.ElementsRepository
import javax.inject.Inject

class FetchNewElement @Inject constructor(
    private val repository: ElementsRepository
) {
    suspend operator fun invoke() = repository.fetchNewElement()
}
