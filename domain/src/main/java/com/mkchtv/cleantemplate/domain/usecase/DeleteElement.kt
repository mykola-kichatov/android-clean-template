package com.mkchtv.cleantemplate.domain.usecase

import com.mkchtv.cleantemplate.domain.repository.ElementsRepository
import javax.inject.Inject

interface DeleteElementUseCase {
    suspend operator fun invoke(vararg ids: Int)
}

class DeleteElement @Inject constructor(
    private val repository: ElementsRepository
) : DeleteElementUseCase {
    override suspend operator fun invoke(vararg ids: Int) = repository.delete(ids = ids)
}