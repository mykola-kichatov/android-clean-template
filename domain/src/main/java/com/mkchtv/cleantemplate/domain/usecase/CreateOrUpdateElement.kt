package com.mkchtv.cleantemplate.domain.usecase

import com.mkchtv.cleantemplate.domain.entity.Element
import com.mkchtv.cleantemplate.domain.repository.ElementsRepository
import javax.inject.Inject

interface CreateOrUpdateElementUseCase {
    suspend operator fun invoke(id: Int, name: String, description: String)
}

class CreateOrUpdateElement @Inject constructor(
    private val repository: ElementsRepository
) : CreateOrUpdateElementUseCase {
    override suspend operator fun invoke(id: Int, name: String, description: String) =
        repository.createOrUpdate(Element(id = id, name = name, description = description))
}