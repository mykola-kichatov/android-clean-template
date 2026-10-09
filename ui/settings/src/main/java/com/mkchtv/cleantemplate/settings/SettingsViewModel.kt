package com.mkchtv.cleantemplate.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkchtv.cleantemplate.domain.auth.usecase.Logout
import com.mkchtv.cleantemplate.domain.common.di.AppIoScope
import com.mkchtv.cleantemplate.domain.settings.usecase.ObserveAppearance
import com.mkchtv.cleantemplate.domain.settings.usecase.SetDynamicColor
import com.mkchtv.cleantemplate.domain.settings.usecase.SetThemeMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
internal class SettingsViewModel @Inject constructor(
    @param:AppIoScope private val appIoScope: CoroutineScope,
    observeAppearance: ObserveAppearance,
    private val setThemeMode: SetThemeMode,
    private val setDynamicColor: SetDynamicColor,
    private val logout: Logout,
) : ViewModel() {

    val uiState: StateFlow<UiState> = observeAppearance()
        .map { appearance ->
            UiState(themeMode = appearance.themeMode, dynamicColor = appearance.dynamicColor)
        }
        .stateIn(
            initialValue = UiState(),
            scope = viewModelScope,
            started = SharingStarted.Lazily,
        )

    // Every write goes on the app scope, so it lands even if the user leaves the screen at once.
    fun onIntent(intent: Intent) = when (intent) {
        is Intent.ThemeModePicked -> appIoScope.launch { setThemeMode(intent.themeMode) }
        is Intent.DynamicColorToggled -> appIoScope.launch { setDynamicColor(intent.enabled) }
        Intent.LogoutConfirmed -> appIoScope.launch { logout() }
    }
}
