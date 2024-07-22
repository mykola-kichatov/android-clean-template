package com.mkchtv.cleantemplate.domain.element.usecase

import com.mkchtv.cleantemplate.domain.element.entity.EditedElementData
import com.mkchtv.cleantemplate.domain.element.repository.ElementsRepository
import javax.inject.Inject

class UpdateElement @Inject constructor(
    private val repository: ElementsRepository,
) {

    suspend operator fun invoke(
        elementId: Int,
        name: String,
        description: String,
        imageUrl: String,
    ) = repository.update(
        elementId = elementId,
        data = EditedElementData(
            name = name,
            description = description,
            imageUrl = imageUrl,
        )
    )
}
