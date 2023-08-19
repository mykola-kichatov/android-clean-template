package com.mkchtv.cleantemplate.playground.menu

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
fun NavGraphBuilder.playgroundMenuScreen(
    onTopicClick: (item: PlaygroundTopic) -> Unit,
) {
    composable(route = NAV_DESTINATION_PLAYGROUND_MENU) {
        PlaygroundMenuScreen(onTopicClick = onTopicClick)
    }
}

const val NAV_DESTINATION_PLAYGROUND_MENU = "playground_menu"