package com.mkchtv.cleantemplate.common.compositionlocal

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.compositionLocalOf

val LocalNavAnimatedVisibilityScope = compositionLocalOf<AnimatedVisibilityScope?> { null }

@ExperimentalSharedTransitionApi
val LocalSharedTransitionScope = compositionLocalOf<SharedTransitionScope?> { null }