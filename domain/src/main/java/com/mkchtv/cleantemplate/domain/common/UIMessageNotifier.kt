package com.mkchtv.cleantemplate.domain.common

interface UIMessageNotifier {

    fun notifyInfo(info: String)
    fun notifyError(error: String)

}