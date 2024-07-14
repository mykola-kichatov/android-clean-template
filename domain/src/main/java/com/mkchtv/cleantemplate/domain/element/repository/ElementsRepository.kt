package com.mkchtv.cleantemplate.domain.element.repository

import com.mkchtv.cleantemplate.domain.element.entity.Element
import kotlinx.coroutines.flow.Flow

interface ElementsRepository {

    fun elementsFlow(): Flow<List<Element>>

    fun elementFlow(elementId: Int): Flow<Element>

    suspend fun createOrUpdate(element: Element)

    suspend fun fetchNewElement()

    suspend fun delete(vararg ids: Int)
}
