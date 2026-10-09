package com.mkchtv.cleantemplate.settings

import android.os.Build
import androidx.annotation.StringRes
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mkchtv.cleantemplate.common.component.ConfirmDialog
import com.mkchtv.cleantemplate.common.preview.ScreenPreviews
import com.mkchtv.cleantemplate.common.preview.ThemedPreview
import com.mkchtv.cleantemplate.domain.settings.entity.ThemeMode
import com.mkchtv.cleantemplate.ui.settings.R
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * @param showDynamicColor whether to show the dynamic color row. Wallpaper colors exist from
 * Android 12 on. Before that the switch would change nothing.
 */
@ExperimentalSharedTransitionApi
@ExperimentalMaterial3Api
@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
@Composable
internal fun SettingsScreen(
    uiState: UiState,
    onIntent: (Intent) -> Unit,
    showDynamicColor: Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S,
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding)
                .verticalScroll(rememberScrollState()),
        ) {
            SectionHeader(textResId = R.string.settings_theme)
            Column(modifier = Modifier.selectableGroup()) {
                ThemeMode.entries.forEach { themeMode ->
                    ThemeModeRow(
                        themeMode = themeMode,
                        selected = themeMode == uiState.themeMode,
                        onClick = { onIntent(Intent.ThemeModePicked(themeMode)) },
                    )
                }
            }
            if (showDynamicColor) {
                HorizontalDivider()
                DynamicColorRow(
                    checked = uiState.dynamicColor,
                    onCheckedChange = { onIntent(Intent.DynamicColorToggled(it)) },
                )
            }
        }
    }

    if (showConfirmLogoutDialog)
        ConfirmDialog(
            title = stringResource(id = R.string.confirm_logout),
            onDismissRequest = { showConfirmLogoutDialog = false },
            onConfirm = {
                showConfirmLogoutDialog = false
                onIntent(Intent.LogoutConfirmed)
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

@Composable
private fun SectionHeader(
    @StringRes textResId: Int,
) = Text(
    text = stringResource(id = textResId),
    modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 16.dp, bottom = 8.dp),
    color = MaterialTheme.colorScheme.primary,
    style = MaterialTheme.typography.titleSmall,
)

// The whole row is selectable, so the tap target is the row, not only the radio button.
@Composable
private fun ThemeModeRow(
    themeMode: ThemeMode,
    selected: Boolean,
    onClick: () -> Unit,
) = Row(
    modifier = Modifier
        .fillMaxWidth()
        .selectable(selected = selected, role = Role.RadioButton, onClick = onClick)
        .padding(horizontal = 24.dp, vertical = 12.dp),
    horizontalArrangement = Arrangement.spacedBy(16.dp),
    verticalAlignment = Alignment.CenterVertically,
) {
    RadioButton(selected = selected, onClick = null)
    Text(
        text = stringResource(id = themeMode.labelResId),
        style = MaterialTheme.typography.bodyLarge,
    )
}

@Composable
private fun DynamicColorRow(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) = Row(
    modifier = Modifier
        .fillMaxWidth()
        .toggleable(value = checked, role = Role.Switch, onValueChange = onCheckedChange)
        .padding(horizontal = 24.dp, vertical = 12.dp),
    horizontalArrangement = Arrangement.spacedBy(16.dp),
    verticalAlignment = Alignment.CenterVertically,
) {
    Column(modifier = Modifier.weight(1f)) {
        Text(
            text = stringResource(id = R.string.settings_dynamic_color),
            style = MaterialTheme.typography.bodyLarge,
        )
        Text(
            text = stringResource(id = R.string.settings_dynamic_color_summary),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
    Switch(checked = checked, onCheckedChange = null)
}

@get:StringRes
private val ThemeMode.labelResId: Int
    get() = when (this) {
        ThemeMode.SYSTEM -> R.string.settings_theme_system
        ThemeMode.LIGHT -> R.string.settings_theme_light
        ThemeMode.DARK -> R.string.settings_theme_dark
    }

@OptIn(
    ExperimentalSharedTransitionApi::class,
    ExperimentalMaterial3Api::class,
    ExperimentalCoroutinesApi::class,
    ExperimentalAnimationApi::class,
)
@ScreenPreviews
@Composable
private fun SettingsScreenPreview() {
    ThemedPreview {
        SettingsScreen(uiState = UiState(), onIntent = {}, showDynamicColor = true)
    }
}

// Android 11 and older: no wallpaper colors, so no row for them.
@OptIn(
    ExperimentalSharedTransitionApi::class,
    ExperimentalMaterial3Api::class,
    ExperimentalCoroutinesApi::class,
    ExperimentalAnimationApi::class,
)
@Preview(name = "portrait, before Android 12", widthDp = 360, heightDp = 640)
@Composable
private fun SettingsScreenNoDynamicColorPreview() {
    ThemedPreview {
        SettingsScreen(uiState = UiState(), onIntent = {}, showDynamicColor = false)
    }
}
