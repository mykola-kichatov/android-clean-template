package com.mkchtv.cleantemplate.settings

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.mkchtv.cleantemplate.common.component.ConfirmDialog
import com.mkchtv.cleantemplate.feature.settings.R
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalSharedTransitionApi
@ExperimentalMaterial3Api
@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
@Composable
internal fun SettingsScreen(
    onLogoutConfirmed: () -> Unit,
) {
    var showConfirmLogoutDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(
                onLogoutRequested = {
                    showConfirmLogoutDialog = true
                },
            )
        },
    ) { scaffoldPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding),
        )
    }

    if (showConfirmLogoutDialog)
        ConfirmDialog(
            title = stringResource(id = R.string.confirm_logout),
            onDismissRequest = { showConfirmLogoutDialog = false },
            onConfirm = {
                showConfirmLogoutDialog = false
                onLogoutConfirmed()
            },
        )
}

@ExperimentalMaterial3Api
@Composable
private fun TopBar(
    onLogoutRequested: () -> Unit,
) = TopAppBar(
    title = { Text(text = stringResource(id = R.string.settings)) },
    actions = {
        IconButton(onClick = onLogoutRequested) {
            Icon(
                painter = painterResource(id = R.drawable.ic_logout),
                contentDescription = stringResource(id = R.string.cd_logout),
            )
        }
    },
)
