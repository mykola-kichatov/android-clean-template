package com.mkchtv.cleantemplate.data.repository

import com.mkchtv.cleantemplate.data.db.ElementsDao
import com.mkchtv.cleantemplate.data.mapper.toDomain
import com.mkchtv.cleantemplate.domain.entity.Element
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
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: Int) {
        TODO("Not yet implemented")
    }

}