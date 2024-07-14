package com.mkchtv.cleantemplate.domain.element.usecase

import com.mkchtv.cleantemplate.domain.element.repository.ElementsRepository
import javax.inject.Inject

class PullNewElement @Inject constructor(
    private val repository: ElementsRepository,
) {
    suspend operator fun invoke() = repository.fetchNewElement()
}
