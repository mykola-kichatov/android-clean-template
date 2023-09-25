@file:OptIn(ExperimentalCoroutinesApi::class)

package com.mkchtv.cleantemplate.playground

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.mkchtv.cleantemplate.playground.menu.NAV_DESTINATION_PLAYGROUND_MENU
import com.mkchtv.cleantemplate.playground.menu.playgroundMenuScreen
import com.mkchtv.cleantemplate.playground.topic.navigateToPlaygroundTopic
import com.mkchtv.cleantemplate.playground.topic.playgroundTopicScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalAnimationApi
fun NavGraphBuilder.playgroundGraph(navController: NavController) {
    navigation(
        startDestination = NAV_DESTINATION_PLAYGROUND_MENU,
        route = NAV_DESTINATION_PLAYGROUND_GRAPH,
    ) {
        playgroundMenuScreen(
            onTopicClick = {
                navController.navigateToPlaygroundTopic(it)
            },
            onBackClick = { navController.popBackStack() },
        )
        playgroundTopicScreen(onBackClick = { navController.popBackStack() })
    }
}

fun NavController.navigateToPlaygroundGraph() {
    navigate(NAV_DESTINATION_PLAYGROUND_GRAPH) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

private const val NAV_DESTINATION_PLAYGROUND_GRAPH = "playground"