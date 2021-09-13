package com.mkchtv.cleantemplate.util

import androidx.lifecycle.SavedStateHandle

fun SavedStateHandle.getStringOrDefault(key: String, default: String): String =
    if (this.contains(key))
        this.get<String>(key) ?: default
    else
        default

fun SavedStateHandle.getIntOrDefault(key: String, default: Int): Int =
    if (this.contains(key))
        this.get<Int>(key) ?: default
    else
        default