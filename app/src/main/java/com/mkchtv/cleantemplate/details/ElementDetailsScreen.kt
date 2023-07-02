package com.mkchtv.cleantemplate.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mkchtv.cleantemplate.common.ConfirmDialog
import com.mkchtv.cleantemplate.list.ElementItem
import com.mkchtv.cleantemplate.list.isNew
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalMaterial3Api
@ExperimentalCoroutinesApi
@Composable
fun ElementDetailsScreen(
    element: ElementItem,
    onBackClick: () -> Unit = {},
    onCreateUpdateConfirmed: () -> Unit = {},
    onDeleteConfirmed: () -> Unit = {}
) {
    val title = remember {
        if (element.isNew()) "Create new element" else "Edit element details"
    }
    var showConfirmDeletionDialog by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = title) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Filled.ArrowBack, "Go back")
                    }
                },
                actions = {
                    if (element.isNew()) {
                        IconButton(onClick = onCreateUpdateConfirmed) {
                            Icon(Icons.Filled.Send, "Create")
                        }
                    } else {
                        IconButton(onClick = { showConfirmDeletionDialog = true }) {
                            Icon(Icons.Filled.Delete, "Delete")
                        }
                        IconButton(onClick = onCreateUpdateConfirmed) {
                            Icon(Icons.Filled.Done, "Done")
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            ListItem(
                overlineContent = { Text(text = element.name) },
                headlineContent = { Text(text = element.description) },
                shadowElevation = 1.0.dp
            )
            if (showConfirmDeletionDialog)
                ConfirmDialog(
                    title = "Delete ${element.name}?",
                    onDismissRequest = { showConfirmDeletionDialog = false },
                    onConfirm = onDeleteConfirmed
                )
        }
    }
}