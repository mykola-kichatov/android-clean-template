package com.mkchtv.cleantemplate.domain.element.repository

import com.mkchtv.cleantemplate.domain.element.entity.EditedElementData
import com.mkchtv.cleantemplate.domain.element.entity.Element
import com.mkchtv.cleantemplate.domain.element.entity.PullElementData
import kotlinx.coroutines.flow.Flow

interface ElementsRepository {

    fun elementsFlow(): Flow<List<Element>>

    fun elementFlow(elementId: Int): Flow<Element?>

    suspend fun create(data: EditedElementData)

    suspend fun update(elementId: Int, data: EditedElementData)

    suspend fun pullElement(): PullElementData

    suspend fun delete(vararg ids: Int)
}
