package com.mkchtv.cleantemplate.playground.menu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PlaygroundMenuScreen(
    onTopicClick: (item: PlaygroundTopic) -> Unit,
) = Column(
    modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
        .verticalScroll(rememberScrollState())
) {
    for (topic in PlaygroundTopic.values()) {
        MenuItem(
            topic = topic,
            onItemClick = onTopicClick,
        )
    }
}

@Composable
private fun MenuItem(
    topic: PlaygroundTopic,
    onItemClick: (item: PlaygroundTopic) -> Unit,
) {
    Text(
        modifier = Modifier
            .clickable { onItemClick(topic) }
            .padding(bottom = 8.dp),
        text = topic.readableName,
        style = MaterialTheme.typography.headlineSmall,
    )
}