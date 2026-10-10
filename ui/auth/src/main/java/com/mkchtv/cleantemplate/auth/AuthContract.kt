package com.mkchtv.cleantemplate.auth

import androidx.compose.runtime.Immutable
import com.mkchtv.cleantemplate.domain.auth.entity.AuthState

// No Effect: logging in has no one-shot event. The gate switches screens by itself.

@Immutable
internal data class UiState(
    /** `null` until the login state has been read. The gate shows a spinner for that long. */
    val authState: AuthState? = null,
)

internal sealed interface Intent {
    data object AuthenticateClick : Intent
}
