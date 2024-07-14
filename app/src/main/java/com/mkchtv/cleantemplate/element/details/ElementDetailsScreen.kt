package com.mkchtv.cleantemplate.element.details

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mkchtv.cleantemplate.R
import com.mkchtv.cleantemplate.common.component.ConfirmDialog
import com.mkchtv.cleantemplate.common.component.Input
import com.mkchtv.cleantemplate.common.component.rememberInputState
import com.mkchtv.cleantemplate.element.list.ElementItem
import com.mkchtv.cleantemplate.element.list.isNew
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalMaterial3Api
@ExperimentalCoroutinesApi
@Composable
fun ElementDetailsScreen(
    element: ElementItem,
    onBackClick: () -> Unit,
    onCreateUpdateConfirmed: (name: String, description: String) -> Unit,
    onDeleteConfirmed: () -> Unit,
) {
    val title = if (element.isNew())
        stringResource(id = R.string.create_new_element)
    else
        stringResource(id = R.string.edit_element_details)
    var showConfirmDeletionDialog by remember { mutableStateOf(false) }
    val nameInputState = rememberInputState(
        hint = stringResource(id = R.string.name),
        initialValue = element.name,
    )
    val descInputState = rememberInputState(
        hint = stringResource(id = R.string.description),
        initialValue = element.description,
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = title) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Filled.ArrowBack, stringResource(id = R.string.cd_go_back))
                    }
                },
                actions = {
                    val createUpdate = {
                        onCreateUpdateConfirmed(
                            nameInputState.value,
                            descInputState.value,
                        )
                    }
                    if (element.isNew()) {
                        IconButton(onClick = createUpdate) {
                            Icon(Icons.Filled.Send, stringResource(id = R.string.cd_create))
                        }
                    } else {
                        IconButton(onClick = { showConfirmDeletionDialog = true }) {
                            Icon(Icons.Filled.Delete, stringResource(id = R.string.cd_delete))
                        }
                        IconButton(onClick = createUpdate) {
                            Icon(Icons.Filled.Done, stringResource(id = R.string.cd_done))
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
                    title = stringResource(id = R.string.confirm_delete, element.name),
                    onDismissRequest = { showConfirmDeletionDialog = false },
                    onConfirm = onDeleteConfirmed
                )
        }
    }
}
