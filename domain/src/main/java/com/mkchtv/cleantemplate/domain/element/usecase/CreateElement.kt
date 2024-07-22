package com.mkchtv.cleantemplate.domain.element.usecase

import com.mkchtv.cleantemplate.domain.element.entity.EditedElementData
import com.mkchtv.cleantemplate.domain.element.repository.ElementsRepository
import javax.inject.Inject

class CreateElement @Inject constructor(
    private val getRandomImageUrl: GetRandomImageUrl,
    private val repository: ElementsRepository,
) {

    suspend operator fun invoke(name: String, description: String) {
        val data = EditedElementData(
            name = name,
            description = description,
            imageUrl = getRandomImageUrl(),
        )
        repository.create(data)
    }
}
