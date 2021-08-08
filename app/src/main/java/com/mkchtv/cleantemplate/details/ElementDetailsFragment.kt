package com.mkchtv.cleantemplate.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mkchtv.cleantemplate.base.BaseFragment
import com.mkchtv.cleantemplate.databinding.FragmentDetailsBinding

class ElementDetailsFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, group: ViewGroup?, state: Bundle?): View {
        val binding = FragmentDetailsBinding.inflate(inflater, group, false)
        return binding.root
    }

}