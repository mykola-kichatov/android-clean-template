package com.mkchtv.cleantemplate.domain.usecase

import com.mkchtv.cleantemplate.domain.entity.Element
import com.mkchtv.cleantemplate.domain.repository.ElementsRepository
import javax.inject.Inject

interface CreateOrUpdateElementUseCase {
    suspend operator fun invoke(element: Element)
}

class CreateOrUpdateElement @Inject constructor(
    private val repository: ElementsRepository
) : CreateOrUpdateElementUseCase {
    override suspend operator fun invoke(element: Element) = repository.createOrUpdate(element)
}