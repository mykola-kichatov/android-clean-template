package com.mkchtv.cleantemplate.common

import com.mkchtv.cleantemplate.domain.common.L10N
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultL10N @Inject constructor(

) : L10N {
    override fun getString(key: String): String {
        TODO("Not yet implemented")
    }
}