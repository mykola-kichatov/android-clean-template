package com.mkchtv.cleantemplate.domain.usecase

import com.mkchtv.cleantemplate.domain.entity.Element
import com.mkchtv.cleantemplate.domain.repository.ElementsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetAllElementsUseCase {
    operator fun invoke(): Flow<List<Element>>
}

class GetAllElements @Inject constructor(
    private val repository: ElementsRepository
) : GetAllElementsUseCase {
    override operator fun invoke() = repository.elementsFlow()
}