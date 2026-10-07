package com.mkchtv.cleantemplate.domain.element.usecase

import com.mkchtv.cleantemplate.domain.element.entity.toEditedData
import com.mkchtv.cleantemplate.domain.element.repository.ElementRepository
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class PullElement @Inject constructor(
    private val getRandomImageUrl: GetRandomImageUrl,
    private val repository: ElementRepository,
) {

    suspend operator fun invoke(): Result<Unit> = runCatching {
        val data = repository.pull()
        repository.create(data.toEditedData(imageUrl = getRandomImageUrl()))
    }.onFailure { error ->
        // runCatching also catches cancellation; pass it on so the caller's coroutine stops
        if (error is CancellationException) throw error
    }
}
