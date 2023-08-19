package com.mkchtv.cleantemplate.playground.menu

enum class PlaygroundTopic(
    val arg: Int,
    val readableName: String,
) {
    ANIMATED_VISIBILITY(0, "Animated visibility");

    companion object {
        fun fromArg(value: Int) = values().first { it.arg == value }
    }
}