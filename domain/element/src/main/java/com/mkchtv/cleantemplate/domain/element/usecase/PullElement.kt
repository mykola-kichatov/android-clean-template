package com.mkchtv.cleantemplate.domain.element.usecase

import com.mkchtv.cleantemplate.domain.element.extension.toEditedData
import com.mkchtv.cleantemplate.domain.element.repository.ElementsRepository
import javax.inject.Inject

class PullElement @Inject constructor(
    private val getRandomImageUrl: GetRandomImageUrl,
    private val repository: ElementsRepository,
) {

    suspend operator fun invoke() = runCatching {
        val data = repository.pullElement()
        repository.create(data.toEditedData(imageUrl = getRandomImageUrl()))
    }
}
