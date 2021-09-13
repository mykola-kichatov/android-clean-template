package com.mkchtv.cleantemplate.mapper

import com.mkchtv.cleantemplate.domain.entity.Element
import com.mkchtv.cleantemplate.list.ElementItem

object ElementMapper {

    fun fromList(items: List<ElementItem>) = items.map { it.toDomain() }

    fun toDomain(item: ElementItem) = Element(
        id = item.id,
        name = item.name,
        description = item.description
    )

}

fun ElementItem.toDomain() = ElementMapper.toDomain(this)

fun List<ElementItem>.toDomain() = ElementMapper.fromList(this)