package com.mkchtv.cleantemplate.list

import androidx.compose.runtime.Immutable
import com.mkchtv.cleantemplate.domain.common.Constants
import com.mkchtv.cleantemplate.domain.entity.Element
import com.mkchtv.cleantemplate.mapper.toUiItem

@Immutable
data class ElementItem(
    val id: Int,
    val name: String,
    val description: String
) {
    companion object {
        val NEW = Element.NEW.toUiItem()
    }
}

fun ElementItem.isNew() = this.id == Constants.NEW_ELEMENT_ID