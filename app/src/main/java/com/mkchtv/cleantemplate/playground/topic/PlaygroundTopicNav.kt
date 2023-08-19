package com.mkchtv.cleantemplate.playground.topic

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mkchtv.cleantemplate.playground.menu.PlaygroundTopic

fun NavGraphBuilder.playgroundTopicScreen(
    onBackClick: () -> Unit = {},
) {
    composable(
        route = "topic/{$NAV_ARG_TOPIC}",
        arguments = listOf(navArgument(NAV_ARG_TOPIC) { type = NavType.IntType })
    ) {
        val arg = it.arguments?.getInt(NAV_ARG_TOPIC) ?: 0
        PlaygroundTopicScreen(
            topic = PlaygroundTopic.fromArg(arg),
            onBackClick = onBackClick,
        )
    }
}

fun NavController.navigateToPlaygroundTopic(topic: PlaygroundTopic) = navigate("topic/${topic.arg}")

private const val NAV_ARG_TOPIC = "topic"