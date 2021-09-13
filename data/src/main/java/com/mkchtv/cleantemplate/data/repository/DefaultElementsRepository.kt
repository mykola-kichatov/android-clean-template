package com.mkchtv.cleantemplate.data.repository

import com.mkchtv.cleantemplate.data.db.ElementsDao
import com.mkchtv.cleantemplate.data.mapper.toDbEntity
import com.mkchtv.cleantemplate.data.mapper.toDomain
import com.mkchtv.cleantemplate.domain.entity.Element
import com.mkchtv.cleantemplate.domain.entity.isNew
import com.mkchtv.cleantemplate.domain.repository.ElementsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultElementsRepository @Inject constructor(
    private val dao: ElementsDao
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

    override suspend fun delete(element: Element) = dao.delete(element.toDbEntity())

}