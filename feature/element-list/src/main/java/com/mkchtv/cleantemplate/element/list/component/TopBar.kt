package com.mkchtv.cleantemplate.element.list.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mkchtv.cleantemplate.feature.element.list.R

@ExperimentalMaterial3Api
@Composable
internal fun TopBar(
    onPullNewElementRequested: () -> Unit,
) = TopAppBar(
    title = { Text(text = stringResource(id = R.string.elements)) },
    actions = {
        IconButton(onClick = onPullNewElementRequested) {
            Icon(
                painter = painterResource(id = R.drawable.ic_pull),
                contentDescription = stringResource(id = R.string.cd_pull_new_element),
            )
        }
    }
)

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun TopBarPreview() {
    Surface {
        TopBar(onPullNewElementRequested = {})
    }
}
