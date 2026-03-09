package com.mkchtv.cleantemplate.domain.element.usecase

import com.mkchtv.cleantemplate.domain.element.repository.ElementRepository
import javax.inject.Inject

class ElementsFlow @Inject constructor(
    private val repository: ElementRepository,
) {

    operator fun invoke() = repository.elementsFlow()
}
