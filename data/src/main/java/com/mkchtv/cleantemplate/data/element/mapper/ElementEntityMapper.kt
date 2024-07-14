package com.mkchtv.cleantemplate.data.element.mapper

import com.mkchtv.cleantemplate.data.element.entity.ElementEntity
import com.mkchtv.cleantemplate.data.element.network.ElementResponse
import com.mkchtv.cleantemplate.domain.element.entity.Element

object ElementEntityMapper {

    fun fromList(elements: List<Element>) = elements.map { it.toDbEntity() }

    fun toDbEntity(element: Element) = ElementEntity(
        id = element.id,
        name = element.name,
        description = element.description
    )

    fun toDbEntity(response: ElementResponse) = ElementEntity(
        name = "Author: ${response.author}",
        description = response.quote
    )
}

fun Element.toDbEntity() = ElementEntityMapper.toDbEntity(this)

fun ElementResponse.toDbEntity() = ElementEntityMapper.toDbEntity(this)

fun List<Element>.toDbEntity() = ElementEntityMapper.fromList(this)
