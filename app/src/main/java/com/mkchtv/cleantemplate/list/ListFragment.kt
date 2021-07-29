package com.mkchtv.cleantemplate.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mkchtv.cleantemplate.base.BaseFragment
import com.mkchtv.cleantemplate.databinding.FragmentListBinding

class ListFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, group: ViewGroup?, state: Bundle?): View {
        val binding = FragmentListBinding.inflate(inflater, group, false)
        return binding.root
    }

}