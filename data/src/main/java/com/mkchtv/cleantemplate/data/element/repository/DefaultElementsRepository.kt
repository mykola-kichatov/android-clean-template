package com.mkchtv.cleantemplate.data.element.repository

import com.mkchtv.cleantemplate.data.element.dao.ElementsDao
import com.mkchtv.cleantemplate.data.element.mapper.toDbEntity
import com.mkchtv.cleantemplate.data.element.mapper.toDomain
import com.mkchtv.cleantemplate.data.element.network.ElementsService
import com.mkchtv.cleantemplate.domain.element.entity.Element
import com.mkchtv.cleantemplate.domain.element.entity.isNew
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

    override fun elementFlow(elementId: Int): Flow<Element> =
        dao.elementFlow(elementId).map { it?.toDomain() ?: Element.NEW }

    override suspend fun createOrUpdate(element: Element) {
        if (element.isNew())
            dao.insert(element.toDbEntity())
        else
            dao.update(element.toDbEntity())
    }

    override suspend fun fetchNewElement() {
        val elementResponse = service.getRandomElement()
        dao.insert(elementResponse.toDbEntity())
    }

    override suspend fun delete(vararg ids: Int) = dao.delete(ids = ids)
}
