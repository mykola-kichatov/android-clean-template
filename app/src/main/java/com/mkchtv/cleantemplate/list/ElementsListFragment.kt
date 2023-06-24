package com.mkchtv.cleantemplate.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.ViewGroupCompat
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.transition.MaterialElevationScale
import com.mkchtv.cleantemplate.base.BaseFragment
import com.mkchtv.cleantemplate.databinding.FragmentListBinding
import com.mkchtv.cleantemplate.domain.common.Constants
import com.mkchtv.cleantemplate.domain.common.Constants.TRANSITION_NAME_ELEMENT_DETAILS
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ElementsListFragment : BaseFragment() {

    private val viewModel: ElementsListViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, group: ViewGroup?, state: Bundle?): View {
        val binding = FragmentListBinding.inflate(inflater, group, false)

        setupTransitionsFor(binding)
        setupList(binding)
        setupFab(binding)

        return binding.root
    }

    private fun setupTransitionsFor(binding: FragmentListBinding) {
        ViewGroupCompat.setTransitionGroup(binding.root, true)
        binding.recyclerView.doOnPreDraw { startPostponedEnterTransition() }
    }

    private fun setupList(binding: FragmentListBinding) {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = ElementsAdapter { view, item -> onListItemClick(view, item.id) }
        collectElementsFlow(adapter)
        binding.recyclerView.adapter = adapter
    }

    private fun onListItemClick(elementView: View, elementId: Int) {
        setupExitTransitions()
        navigateToDetails(elementView, elementId)
    }

    private fun setupExitTransitions() {
        exitTransition = MaterialElevationScale(false).apply {
            duration = Constants.TRANSITION_DURATION
        }
        reenterTransition = MaterialElevationScale(true).apply {
            duration = Constants.TRANSITION_DURATION
        }
    }

    private fun navigateToDetails(clickedView: View, elementId: Int) {
        val extras = FragmentNavigatorExtras(clickedView to TRANSITION_NAME_ELEMENT_DETAILS)
        val directions =
            ElementsListFragmentDirections.actionListFragmentToDetailsFragment(elementId)
        findNavController(this).navigate(directions, extras)
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

    private fun setupFab(binding: FragmentListBinding) {
        ViewCompat.setTransitionName(binding.fab, Constants.TRANSITION_NAME_LIST_FAB)
        binding.fab.setOnClickListener { fab ->
            setupExitTransitions()
            navigateToDetails(fab, Constants.NEW_ELEMENT_ID)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
    }
}