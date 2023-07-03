package com.mkchtv.cleantemplate.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.mkchtv.cleantemplate.common.Input
import com.mkchtv.cleantemplate.common.rememberInputState
import com.mkchtv.cleantemplate.list.ElementItem
import com.mkchtv.cleantemplate.list.isNew
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalMaterial3Api
@ExperimentalCoroutinesApi
@Composable
fun ElementDetailsScreen(
    element: ElementItem,
    onBackClick: () -> Unit = {},
    onCreateUpdateConfirmed: (name: String, description: String) -> Unit = { _, _ -> },
    onDeleteConfirmed: () -> Unit = {}
) {
    val title = remember(element) {
        if (element.isNew()) "Create new element" else "Edit element details"
    }
    var showConfirmDeletionDialog by remember { mutableStateOf(false) }
    val nameInputState = rememberInputState(hint = "name", initialValue = element.name)
    val descInputState = rememberInputState(
        hint = "description",
        initialValue = element.description
    )

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
                    val createUpdate = {
                        onCreateUpdateConfirmed(
                            nameInputState.value,
                            descInputState.value
                        )
                    }
                    if (element.isNew()) {
                        IconButton(onClick = createUpdate) {
                            Icon(Icons.Filled.Send, "Create")
                        }
                    } else {
                        IconButton(onClick = { showConfirmDeletionDialog = true }) {
                            Icon(Icons.Filled.Delete, "Delete")
                        }
                        IconButton(onClick = createUpdate) {
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
            Column(
                modifier = Modifier.padding(16.dp),
            ) {
                Input(state = nameInputState)
                Spacer(modifier = Modifier.height(8.dp))
                Input(state = descInputState)
            }
            if (showConfirmDeletionDialog)
                ConfirmDialog(
                    title = "Delete ${element.name}?",
                    onDismissRequest = { showConfirmDeletionDialog = false },
                    onConfirm = onDeleteConfirmed
                )
        }
    }
}