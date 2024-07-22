package com.mkchtv.cleantemplate.data.element.repository

import com.mkchtv.cleantemplate.data.element.dao.ElementsDao
import com.mkchtv.cleantemplate.data.element.extension.toDbEntity
import com.mkchtv.cleantemplate.data.element.extension.toPullData
import com.mkchtv.cleantemplate.data.element.mapper.toDomain
import com.mkchtv.cleantemplate.data.element.network.ElementsService
import com.mkchtv.cleantemplate.domain.element.entity.EditedElementData
import com.mkchtv.cleantemplate.domain.element.entity.Element
import com.mkchtv.cleantemplate.domain.element.entity.PullElementData
import com.mkchtv.cleantemplate.domain.element.repository.ElementsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultElementsRepository @Inject constructor(
    private val dao: ElementsDao,
    private val service: ElementsService,
) : ElementsRepository {

    override fun elementsFlow(): Flow<List<Element>> =
        dao.elementsFlow().map { it.toDomain() }

    override fun elementFlow(elementId: Int): Flow<Element?> =
        dao.elementFlow(elementId).map { it?.toDomain() }

    override suspend fun create(data: EditedElementData) = dao.insert(data.toDbEntity())

    override suspend fun update(elementId: Int, data: EditedElementData) =
        dao.update(data.toDbEntity(elementId))

    override suspend fun pullElement(): PullElementData = service.getRandomElement().toPullData()

    override suspend fun delete(vararg ids: Int) = dao.delete(ids = ids)
}
