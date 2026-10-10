package com.mkchtv.cleantemplate.common.component

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SplashScreenHoldTest {

    @Test
    fun `nothing holds the splash at first`() {
        // [GIVEN]
        // [WHEN]
        val hold = SplashScreenHold()

        // [THEN]
        Assertions.assertFalse(hold.isHeld)
    }

    @Test
    fun `the splash stays until every screen holding it lets go`() {
        // [GIVEN] two screens hold the splash
        val hold = SplashScreenHold()
        hold.acquire()
        hold.acquire()

        // [WHEN] one lets go
        hold.release()

        // [THEN]
        Assertions.assertTrue(hold.isHeld)

        // [WHEN] the other lets go too
        hold.release()

        // [THEN]
        Assertions.assertFalse(hold.isHeld)
    }
}
