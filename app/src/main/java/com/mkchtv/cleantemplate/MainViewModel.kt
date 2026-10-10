package com.mkchtv.cleantemplate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkchtv.cleantemplate.domain.settings.entity.Appearance
import com.mkchtv.cleantemplate.domain.settings.usecase.ObserveAppearance
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel @Inject constructor(
    observeAppearance: ObserveAppearance,
) : ViewModel() {

    /**
     * `null` until the stored appearance has been read. The splash screen stays up at least that
     * long, so the app is drawn once, in the right theme, instead of flashing the phone's.
     *
     * Eagerly, not Lazily: the splash is held on the value, so nothing may depend on a collector
     * arriving first. If one never did, the app would look hung. Screen ViewModels keep Lazily,
     * where the point is surviving a tab switch.
     */
    val appearance: StateFlow<Appearance?> = observeAppearance()
        .stateIn(
            initialValue = null,
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
        )
}
