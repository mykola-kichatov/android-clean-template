package com.mkchtv.cleantemplate.common.compositionlocal

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import com.mkchtv.cleantemplate.common.component.SplashScreenHold

val LocalAnimatedVisibilityScope = compositionLocalOf<AnimatedVisibilityScope?> { null }

@ExperimentalSharedTransitionApi
val LocalSharedTransitionScope = compositionLocalOf<SharedTransitionScope?> { null }

/** Provided by `MainActivity`. Without it, in previews and tests, holding the splash does nothing. */
val LocalSplashScreenHold = staticCompositionLocalOf { SplashScreenHold() }
