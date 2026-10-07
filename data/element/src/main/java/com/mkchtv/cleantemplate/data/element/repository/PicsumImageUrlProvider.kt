package com.mkchtv.cleantemplate.data.element.repository

import com.mkchtv.cleantemplate.domain.element.repository.ImageUrlProvider
import javax.inject.Inject
import kotlin.random.Random

internal class PicsumImageUrlProvider(
    private val random: Random,
) : ImageUrlProvider {

    @Inject
    constructor() : this(Random.Default)

    override fun randomImageUrl(): String {
        val width = 300 + random.nextInt(0, 200)
        val height = width * 4 / 3
        return "https://picsum.photos/$width/$height"
    }
}
