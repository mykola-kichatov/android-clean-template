package com.mkchtv.cleantemplate.details

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.transition.MaterialContainerTransform
import com.mkchtv.cleantemplate.R
import com.mkchtv.cleantemplate.base.BaseFragment
import com.mkchtv.cleantemplate.databinding.FragmentDetailsBinding
import com.mkchtv.cleantemplate.domain.common.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ElementDetailsFragment : BaseFragment() {

    private val viewModel: ElementDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupEnterTransitions()
    }

    private fun setupEnterTransitions() {
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.navHostContainer
            duration = Constants.TRANSITION_DURATION
            scrimColor = Color.WHITE
            endContainerColor = Color.WHITE
            startContainerColor = Color.WHITE
            setAllContainerColors(Color.WHITE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, group: ViewGroup?, state: Bundle?): View {
        val binding = FragmentDetailsBinding.inflate(inflater, group, false)

        setupTransitionsFor(binding)
        collectElementFlow(binding.name, binding.description)

        return binding.root
    }

    private fun setupTransitionsFor(binding: FragmentDetailsBinding) {
        ViewCompat.setTransitionName(binding.cardView, Constants.TRANSITION_NAME_ELEMENT_DETAILS)
    }

    private fun collectElementFlow(name: TextView, description: TextView) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.elementState.collect { item ->
                    name.text = item.name
                    description.text = item.description
                }
            }
        }
    }
}