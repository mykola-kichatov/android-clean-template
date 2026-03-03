package com.mkchtv.cleantemplate.domain.element.usecase

import com.mkchtv.cleantemplate.domain.element.repository.ElementRepository
import javax.inject.Inject

class ElementFlow @Inject constructor(
    private val repository: ElementRepository,
) {

    operator fun invoke(elementId: Int) = repository.elementFlow(elementId)
}
