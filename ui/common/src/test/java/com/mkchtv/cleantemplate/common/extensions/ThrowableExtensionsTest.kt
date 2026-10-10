package com.mkchtv.cleantemplate.common.extensions

import com.mkchtv.cleantemplate.ui.common.R
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.net.UnknownHostException

class ThrowableExtensionsTest {

    @Test
    fun `a network error gets the no connection message`() {
        // [GIVEN]
        val error = UnknownHostException("Unable to resolve host \"dummyjson.com\"")

        // [WHEN]
        val messageRes = error.toMessageRes()

        // [THEN]
        Assertions.assertEquals(R.string.error_no_connection, messageRes)
    }

    @Test
    fun `any other error gets the generic message`() {
        // [GIVEN]
        val error = IllegalStateException("HTTP 500 Internal Server Error")

        // [WHEN]
        val messageRes = error.toMessageRes()

        // [THEN]
        Assertions.assertEquals(R.string.error_unknown, messageRes)
    }
}
