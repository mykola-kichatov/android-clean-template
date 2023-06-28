package com.mkchtv.cleantemplate.details

import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun ElementDetailsScreen(
    viewModel: ElementDetailsViewModel,
) {
    val elementState = viewModel.elementState.collectAsState()
    ListItem(
        overlineContent = { Text(text = elementState.value.name) },
        headlineContent = { Text(text = elementState.value.description) },
        shadowElevation = 1.0.dp
    )
}