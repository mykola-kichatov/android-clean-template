package com.mkchtv.cleantemplate.domain.usecase

import com.mkchtv.cleantemplate.domain.entity.Element
import com.mkchtv.cleantemplate.domain.repository.ElementsRepository
import javax.inject.Inject

interface DeleteElementUseCase {
    suspend operator fun invoke(element: Element)
}

class DeleteElement @Inject constructor(
    private val repository: ElementsRepository
) : DeleteElementUseCase {
    override suspend operator fun invoke(element: Element) = repository.delete(element)
}