package com.mkchtv.cleantemplate.mapper

import com.mkchtv.cleantemplate.domain.entity.Element
import com.mkchtv.cleantemplate.list.ElementItem

object ElementItemMapper {

    fun fromList(elements: List<Element>) = elements.map { it.toUiItem() }

    fun fromDomain(element: Element) = ElementItem(
        id = element.id,
        name = element.name,
        description = element.description
    )

}

fun Element.toUiItem() = ElementItemMapper.fromDomain(this)

fun List<Element>.toUiItemsList() = ElementItemMapper.fromList(this)