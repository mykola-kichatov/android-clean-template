package com.mkchtv.cleantemplate.data.element.network

import kotlinx.serialization.Serializable

@Serializable
internal data class ElementResponse(
    val quote: String,
    val author: String,
)
