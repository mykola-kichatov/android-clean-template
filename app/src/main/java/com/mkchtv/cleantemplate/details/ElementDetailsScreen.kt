package com.mkchtv.cleantemplate.details

import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.mkchtv.cleantemplate.list.ElementItem
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun ElementDetailsScreen(
    element: ElementItem,
) {
    ListItem(
        overlineContent = { Text(text = element.name) },
        headlineContent = { Text(text = element.description) },
        shadowElevation = 1.0.dp
    )
}