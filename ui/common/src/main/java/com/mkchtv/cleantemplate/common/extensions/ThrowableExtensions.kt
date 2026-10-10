package com.mkchtv.cleantemplate.common.extensions

import androidx.annotation.StringRes
import com.mkchtv.cleantemplate.ui.common.R
import java.io.IOException

/**
 * The message to show the user for this error, as a string resource. Never show
 * [Throwable.message]: it is technical, English only, and can be null.
 *
 * Here an [IOException] comes from the network: no connection, an unknown host, a timeout.
 * DataStore throws one too, for a broken file, so a feature that writes one needs its own
 * message. Anything else, an HTTP error status included, gets the generic message.
 */
@StringRes
fun Throwable.toMessageRes(): Int = when (this) {
    is IOException -> R.string.error_no_connection
    else -> R.string.error_unknown
}
