package com.mkchtv.cleantemplate.common

import android.content.Context
import com.mkchtv.cleantemplate.domain.common.L10N
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultL10N @Inject constructor(
    @ApplicationContext private val context: Context
) : L10N {

    override fun getString(key: String): String {
        val packageName: String = context.packageName
        val resId: Int = context.resources.getIdentifier(key, "string", packageName)
        return if (resId == 0x0) "" else context.resources.getString(resId)
    }

}