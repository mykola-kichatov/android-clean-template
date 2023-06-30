package com.mkchtv.cleantemplate.domain.usecase

import com.mkchtv.cleantemplate.domain.entity.Element
import com.mkchtv.cleantemplate.domain.repository.ElementsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetElementUseCase {
    operator fun invoke(elementId: Int): Flow<Element>
}

class GetElement @Inject constructor(
    private val repository: ElementsRepository
) : GetElementUseCase {
    override operator fun invoke(elementId: Int) = repository.elementFlow(elementId)
}