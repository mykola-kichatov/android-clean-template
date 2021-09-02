package com.mkchtv.cleantemplate.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mkchtv.cleantemplate.base.BaseFragment
import com.mkchtv.cleantemplate.databinding.FragmentListBinding
import com.mkchtv.cleantemplate.domain.common.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ElementsListFragment : BaseFragment() {

    private val viewModel: ElementsListViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, group: ViewGroup?, state: Bundle?): View {
        val binding = FragmentListBinding.inflate(inflater, group, false)

        setupList(binding)
        setupFab(binding)

        return binding.root
    }

    private fun setupList(binding: FragmentListBinding) {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = ElementsAdapter { navigateToDetails(it.id) }
        collectElementsFlow(adapter)
        binding.recyclerView.adapter = adapter
    }

    private fun navigateToDetails(elementId: Int) {
        val action = ElementsListFragmentDirections.actionListFragmentToDetailsFragment(elementId)
        findNavController(this).navigate(action)
    }

    private fun collectElementsFlow(adapter: ElementsAdapter) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.elementsState.collect { items ->
                    adapter.submitList(items)
                }
            }
        }
    }

    private fun setupFab(binding: FragmentListBinding) =
        binding.fab.setOnClickListener { navigateToDetails(Constants.NEW_ELEMENT_ID) }

}