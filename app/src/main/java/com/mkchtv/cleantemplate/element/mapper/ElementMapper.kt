package com.mkchtv.cleantemplate.element.mapper

import com.mkchtv.cleantemplate.domain.element.entity.Element
import com.mkchtv.cleantemplate.element.list.ElementItem

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
