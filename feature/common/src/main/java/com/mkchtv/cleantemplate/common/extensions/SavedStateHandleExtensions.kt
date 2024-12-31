package com.mkchtv.cleantemplate.common.extensions

import androidx.lifecycle.SavedStateHandle

fun SavedStateHandle.getIntOrDefault(key: String, default: Int): Int =
    if (this.contains(key))
        this.get<Int>(key) ?: default
    else
        default
