package com.mkchtv.cleantemplate.data.element.mapper

import com.mkchtv.cleantemplate.data.element.entity.ElementEntity
import com.mkchtv.cleantemplate.domain.element.entity.Element

object ElementMapper {

    fun fromList(elements: List<ElementEntity>) = elements.map { it.toDomain() }

    fun toDomain(entity: ElementEntity) = Element(
        id = entity.id,
        name = entity.name,
        description = entity.description,
        imageUrl = entity.imageUrl,
    )
}

fun ElementEntity.toDomain() = ElementMapper.toDomain(this)

fun List<ElementEntity>.toDomain() = ElementMapper.fromList(this)
