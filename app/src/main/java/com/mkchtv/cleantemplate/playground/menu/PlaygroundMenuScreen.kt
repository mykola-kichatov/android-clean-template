@file:OptIn(ExperimentalMaterial3Api::class)

package com.mkchtv.cleantemplate.playground.menu

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mkchtv.cleantemplate.common.CardListItem

@Composable
fun PlaygroundMenuScreen(
    onTopicClick: (item: PlaygroundTopic) -> Unit,
    onBackClick: () -> Unit = {},
) = Scaffold(
    topBar = {
        TopAppBar(
            title = { Text(text = "Playground") },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.Filled.ArrowBack, "Go back")
                }
            },
        )
    }
) { paddingValues ->
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        for (topic in PlaygroundTopic.values()) {
            MenuItem(
                topic = topic,
                onItemClick = onTopicClick,
            )
            Divider(thickness = 8.dp, color = Color.Transparent)
        }
    }
}

@Composable
private fun MenuItem(
    topic: PlaygroundTopic,
    onItemClick: (item: PlaygroundTopic) -> Unit,
) = CardListItem(headlineText = topic.readableName) {
    onItemClick(topic)
}