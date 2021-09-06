package com.mkchtv.cleantemplate.list

import com.mkchtv.cleantemplate.domain.entity.Element
import com.mkchtv.cleantemplate.mapper.toUiItem

data class ElementItem(
    val id: Int,
    val name: String,
    val description: String
) {
    companion object {
        val NEW = Element.NEW.toUiItem()
    }
}