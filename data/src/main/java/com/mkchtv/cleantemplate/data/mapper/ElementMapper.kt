package com.mkchtv.cleantemplate.data.mapper

import com.mkchtv.cleantemplate.data.entity.ElementEntity
import com.mkchtv.cleantemplate.domain.entity.Element

object ElementMapper {

    fun fromList(elements: List<ElementEntity>) = elements.map { it.toDomain() }

    fun toDomain(entity: ElementEntity) = Element(
        id = entity.id,
        name = entity.name,
        description = entity.description
    )
}

fun ElementEntity.toDomain() = ElementMapper.toDomain(this)

fun List<ElementEntity>.toDomain() = ElementMapper.fromList(this)
