package com.mkchtv.cleantemplate.domain.usecase

import com.mkchtv.cleantemplate.domain.repository.ElementsRepository
import javax.inject.Inject

class GetAllElements @Inject constructor(
    private val repository: ElementsRepository
) {
    operator fun invoke() = repository.elementsFlow()
}