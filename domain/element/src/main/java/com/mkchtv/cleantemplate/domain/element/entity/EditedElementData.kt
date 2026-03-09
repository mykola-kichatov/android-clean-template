package com.mkchtv.cleantemplate.domain.element.entity

data class EditedElementData(
    val name: String,
    val description: String,
    val imageUrl: String,
)

fun PullElementData.toEditedData(imageUrl: String) = EditedElementData(
    name = name,
    description = description,
    imageUrl = imageUrl,
)
