package com.mkchtv.cleantemplate.data.mapper

import com.mkchtv.cleantemplate.data.entity.ElementEntity
import com.mkchtv.cleantemplate.domain.entity.Element

object ElementEntityMapper {

    fun fromList(elements: List<Element>) = elements.map { it.toDbEntity() }

    fun toDbEntity(element: Element) = ElementEntity(
        id = element.id,
        name = element.name,
        description = element.description
    )

}

fun Element.toDbEntity() = ElementEntityMapper.toDbEntity(this)

fun List<Element>.toDbEntity() = ElementEntityMapper.fromList(this)