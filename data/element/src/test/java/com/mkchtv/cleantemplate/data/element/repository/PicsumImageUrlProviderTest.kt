package com.mkchtv.cleantemplate.data.element.repository

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import kotlin.random.Random

class PicsumImageUrlProviderTest {

    @Test
    fun `the same seed gives the same urls`() {
        // [GIVEN]
        val first = PicsumImageUrlProvider(Random(42))
        val second = PicsumImageUrlProvider(Random(42))

        // [WHEN]
        val firstUrls = List(10) { first.randomImageUrl() }
        val secondUrls = List(10) { second.randomImageUrl() }

        // [THEN]
        Assertions.assertEquals(firstUrls, secondUrls)
    }

    @Test
    fun `url is a picsum image 300 to 499 px wide in a 3 to 4 shape`() {
        // [GIVEN]
        val provider = PicsumImageUrlProvider(Random(42))
        val urlPattern = Regex("""https://picsum\.photos/(\d+)/(\d+)""")

        // [WHEN]
        val urls = List(100) { provider.randomImageUrl() }

        // [THEN]
        urls.forEach { url ->
            val match = urlPattern.matchEntire(url)
            Assertions.assertNotNull(match, url)
            val (width, height) = match!!.destructured.toList().map { it.toInt() }
            Assertions.assertTrue(width in 300..499, url)
            Assertions.assertEquals(width * 4 / 3, height, url)
        }
    }
}
