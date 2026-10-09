package com.mkchtv.cleantemplate.settings

import androidx.compose.runtime.Immutable
import com.mkchtv.cleantemplate.domain.settings.entity.ThemeMode

// No Effect: the screen has no one-shot events. After a logout the auth gate reacts by itself.

@Immutable
internal data class UiState(
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val dynamicColor: Boolean = true,
)

internal sealed interface Intent {
    data class ThemeModePicked(val themeMode: ThemeMode) : Intent
    data class DynamicColorToggled(val enabled: Boolean) : Intent
    data object LogoutConfirmed : Intent
}
