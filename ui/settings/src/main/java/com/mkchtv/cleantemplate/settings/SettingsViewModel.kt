package com.mkchtv.cleantemplate.settings

import androidx.lifecycle.ViewModel
import com.mkchtv.cleantemplate.domain.auth.usecase.Logout
import com.mkchtv.cleantemplate.domain.common.di.AppIoScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
internal class SettingsViewModel @Inject constructor(
    @AppIoScope private val appIoScope: CoroutineScope,
    private val logout: Logout,
) : ViewModel() {

    fun onLogoutConfirmed() = appIoScope.launch {
        logout()
    }
}
