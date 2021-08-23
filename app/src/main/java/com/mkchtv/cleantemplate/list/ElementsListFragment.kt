package com.mkchtv.cleantemplate.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.mkchtv.cleantemplate.base.BaseFragment
import com.mkchtv.cleantemplate.databinding.FragmentListBinding
import com.mkchtv.cleantemplate.domain.common.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ElementsListFragment : BaseFragment() {

    private val viewModel: ElementsListViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, group: ViewGroup?, state: Bundle?): View {
        val binding = FragmentListBinding.inflate(inflater, group, false)

        setupFab(binding)

        return binding.root
    }

    private fun setupFab(binding: FragmentListBinding) =
        binding.fab.setOnClickListener {
            navigateToDetails(Constants.NEW_ELEMENT_ID)
        }

    private fun navigateToDetails(elementId: Int) {
        val action = ElementsListFragmentDirections.actionListFragmentToDetailsFragment(elementId)
        findNavController(this).navigate(action)
    }

}