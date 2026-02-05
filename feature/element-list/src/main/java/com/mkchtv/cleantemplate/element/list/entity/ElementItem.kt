package com.mkchtv.cleantemplate.element.list.entity

import androidx.compose.runtime.Immutable
import com.mkchtv.cleantemplate.domain.element.entity.Element

@Immutable
internal data class ElementItem(
    val id: Int,
    val name: String,
    val description: String,
    val imageUrl: String,
)

internal fun Element.toUiItem() = ElementItem(
    id = id,
    name = name,
    description = description,
    imageUrl = imageUrl,
)

internal fun List<Element>.toUiItems() = map { it.toUiItem() }
