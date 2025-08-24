package com.mkchtv.cleantemplate.data.element.network

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class ElementResponse(
    val quote: String,
    val author: String,
)
