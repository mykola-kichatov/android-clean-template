package com.mkchtv.cleantemplate.domain.repository

import com.mkchtv.cleantemplate.domain.entity.Element

interface ElementsRepository {

    suspend fun getAll(): List<Element>

    suspend fun createOrUpdate(element: Element)

    suspend fun delete(id: Int)

}