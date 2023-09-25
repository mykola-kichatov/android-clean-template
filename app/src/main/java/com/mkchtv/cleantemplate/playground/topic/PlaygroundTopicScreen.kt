@file:OptIn(ExperimentalMaterial3Api::class)

package com.mkchtv.cleantemplate.playground.topic

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mkchtv.cleantemplate.playground.menu.PlaygroundTopic
import com.mkchtv.cleantemplate.playground.menu.PlaygroundTopic.*
import com.mkchtv.cleantemplate.playground.topic.animatedvisibility.AnimatedVisibilityTopic
import com.mkchtv.cleantemplate.playground.topic.deeplink.DeepLinkTopic

@Composable
fun PlaygroundTopicScreen(
    topic: PlaygroundTopic,
    onBackClick: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = topic.readableName) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Filled.ArrowBack, "Go back")
                    }
                },
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (topic) {
                ANIMATED_VISIBILITY -> AnimatedVisibilityTopic()
                DEEP_LINK -> DeepLinkTopic()
            }
        }
    }
}