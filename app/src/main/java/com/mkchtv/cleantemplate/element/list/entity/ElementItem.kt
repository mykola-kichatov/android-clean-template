package com.mkchtv.cleantemplate.element.list.entity

import androidx.compose.runtime.Immutable

@Immutable
data class ElementItem(
    val id: Int,
    val name: String,
    val description: String,
    val imageUrl: String,
)
