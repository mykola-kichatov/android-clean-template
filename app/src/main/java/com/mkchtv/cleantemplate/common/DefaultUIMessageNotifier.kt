package com.mkchtv.cleantemplate.common

import android.content.Context
import android.widget.Toast
import com.mkchtv.cleantemplate.domain.common.UIMessageNotifier
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultUIMessageNotifier @Inject constructor(
    @ApplicationContext private val context: Context
) : UIMessageNotifier {

    override fun notifyInfo(info: String) {
        Toast.makeText(context, info, Toast.LENGTH_SHORT).show()
    }

    override fun notifyError(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
    }

}