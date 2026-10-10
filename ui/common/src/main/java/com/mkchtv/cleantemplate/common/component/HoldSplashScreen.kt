package com.mkchtv.cleantemplate.common.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import com.mkchtv.cleantemplate.common.compositionlocal.LocalSplashScreenHold

/**
 * Keeps the app's splash screen up while this is in the composition, so a screen that still reads
 * what its first frame needs shows no spinner right after the splash. Once the splash is gone, it
 * does nothing.
 */
@Composable
fun HoldSplashScreen() {
    val hold = LocalSplashScreenHold.current
    DisposableEffect(hold) {
        hold.acquire()
        onDispose { hold.release() }
    }
}

/**
 * Counts the screens that hold the splash screen, see [HoldSplashScreen]. `MainActivity` provides
 * one in [LocalSplashScreenHold] and keeps the splash up while [isHeld].
 */
class SplashScreenHold {

    private var holders = 0

    val isHeld: Boolean
        get() = holders > 0

    internal fun acquire() {
        holders++
    }

    internal fun release() {
        holders--
    }
}
