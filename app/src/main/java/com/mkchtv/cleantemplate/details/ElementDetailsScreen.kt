package com.mkchtv.cleantemplate.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ElementDetailsScreen(viewModel: ElementDetailsViewModel) {

    Card(
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.surface
    ) {
        val elementState = viewModel.elementState.collectAsState()
        Column {
            TextField(
                value = elementState.value.name,
                onValueChange = { viewModel.onNameTextChanged(it) }
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = elementState.value.description,
                onValueChange = { viewModel.onDescriptionTextChanged(it) }
            )
        }

    }

}