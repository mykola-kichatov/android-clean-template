package com.mkchtv.cleantemplate.element.details.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import com.mkchtv.cleantemplate.domain.element.entity.Element
import com.mkchtv.cleantemplate.ui.element.details.R
import com.mkchtv.cleantemplate.ui.common.R as commonRes

@ExperimentalMaterial3Api
@Composable
internal fun TopBar(
    isScreenLoading: Boolean = true,
    element: Element? = null,
    onBackClick: () -> Unit,
    onDeleteRequested: () -> Unit,
) {
    val titleRes: Int = remember(isScreenLoading, element) {
        when {
            isScreenLoading -> commonRes.string.loading
            element == null -> R.string.create_new_element
            else -> R.string.edit_element_details
        }
    }

    val title = stringResource(id = titleRes)

    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(id = commonRes.string.cd_go_back),
                )
            }
        },
        actions = {
            if (element != null)
                IconButton(onClick = onDeleteRequested) {
                    Icon(Icons.Filled.Delete, stringResource(id = commonRes.string.cd_delete))
                }
        }
    )
}
