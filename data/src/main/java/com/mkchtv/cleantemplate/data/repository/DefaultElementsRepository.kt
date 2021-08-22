package com.mkchtv.cleantemplate.data.repository

import com.mkchtv.cleantemplate.domain.entity.Element
import com.mkchtv.cleantemplate.domain.repository.ElementsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultElementsRepository @Inject constructor(

) : ElementsRepository {

    override suspend fun getAll(): List<Element> {
        TODO("Not yet implemented")
    }

    override suspend fun createOrUpdate(element: Element) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: Int) {
        TODO("Not yet implemented")
    }

}