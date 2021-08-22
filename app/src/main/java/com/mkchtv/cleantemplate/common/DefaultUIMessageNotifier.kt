package com.mkchtv.cleantemplate.common

import com.mkchtv.cleantemplate.domain.common.UIMessageNotifier
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultUIMessageNotifier @Inject constructor(

) : UIMessageNotifier {

    override fun notifyInfo(info: String) {
        TODO("Not yet implemented")
    }

    override fun notifyError(error: String) {
        TODO("Not yet implemented")
    }

}