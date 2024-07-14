package com.mkchtv.cleantemplate.domain.element.usecase

import com.mkchtv.cleantemplate.domain.element.entity.Element
import com.mkchtv.cleantemplate.domain.element.repository.ElementsRepository
import javax.inject.Inject

class CreateOrUpdateElement @Inject constructor(
    private val repository: ElementsRepository,
) {
    suspend operator fun invoke(id: Int, name: String, description: String) =
        repository.createOrUpdate(Element(id = id, name = name, description = description))
}
