package com.mkchtv.cleantemplate.domain.repository

import com.mkchtv.cleantemplate.domain.entity.Element
import kotlinx.coroutines.flow.Flow

interface ElementsRepository {

    fun elementsFlow(): Flow<List<Element>>

    suspend fun createOrUpdate(element: Element)

    suspend fun delete(id: Int)

}