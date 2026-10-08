package com.mkchtv.cleantemplate.data.element.di

import com.mkchtv.cleantemplate.data.element.network.ElementResponse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class NetworkModuleTest {

    @Test
    fun `retrofit parses a quote and skips fields the response does not declare`() {
        // [GIVEN]
        val retrofit = NetworkModule.provideRetrofit(
            okHttpClient = NetworkModule.providesOkHttpClient(),
            json = NetworkModule.providesJson(),
        )
        val converter = retrofit.responseBodyConverter<ElementResponse>(
            ElementResponse::class.java,
            emptyArray(),
        )
        val body = """{"id":456,"quote":"Have heart.","author":"Muhammad Ali"}"""
            .toResponseBody("application/json".toMediaType())

        // [WHEN]
        val response = converter.convert(body)

        // [THEN]
        Assertions.assertEquals(
            ElementResponse(quote = "Have heart.", author = "Muhammad Ali"),
            response,
        )
    }
}
