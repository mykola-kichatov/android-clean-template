package com.mkchtv.cleantemplate.domain.element.usecase

import com.mkchtv.cleantemplate.domain.element.repository.ElementsRepository
import javax.inject.Inject

class DeleteElement @Inject constructor(
    private val repository: ElementsRepository,
) {

    suspend operator fun invoke(vararg ids: Int) = repository.delete(ids = ids)
}
