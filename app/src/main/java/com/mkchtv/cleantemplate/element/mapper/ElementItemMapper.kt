package com.mkchtv.cleantemplate.element.mapper

import com.mkchtv.cleantemplate.domain.element.entity.Element
import com.mkchtv.cleantemplate.element.entity.ElementItem

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
