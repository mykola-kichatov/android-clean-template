package com.mkchtv.cleantemplate.domain.element.entity

import com.mkchtv.cleantemplate.domain.common.Constants

data class Element(
    val id: Int,
    val name: String,
    val description: String,
) {
    companion object {
        val NEW = Element(
            id = Constants.NEW_ELEMENT_ID,
            name = "",
            description = "",
        )
    }
}

fun Element.isNew() = this.id == Constants.NEW_ELEMENT_ID
