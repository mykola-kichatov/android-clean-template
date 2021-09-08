package com.mkchtv.cleantemplate.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mkchtv.cleantemplate.databinding.ListItemElementBinding
import com.mkchtv.cleantemplate.domain.common.Constants

class ElementsAdapter(
    private val onItemClicked: (View, ElementItem) -> Unit
) : ListAdapter<ElementItem, ElementsAdapter.ElementViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElementViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemElementBinding.inflate(inflater, parent, false)
        val viewHolder = ElementViewHolder(binding)
        viewHolder.cardView.setOnClickListener {
            val position = viewHolder.adapterPosition
            onItemClicked(it, getItem(position))
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ElementViewHolder, position: Int) =
        holder.bind(getItem(position))

    class ElementViewHolder(
        private val binding: ListItemElementBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        val cardView = binding.cardView

        fun bind(item: ElementItem) {
            binding.name.text = item.name
            binding.description.text = item.description
            setupTransition(item)
        }

        private fun setupTransition(item: ElementItem) {
            ViewCompat.setTransitionName(
                binding.cardView,
                "${Constants.TRANSITION_NAME_ELEMENT_ITEM}${item.id}"
            )
        }

    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<ElementItem>() {
            override fun areItemsTheSame(oldItem: ElementItem, newItem: ElementItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ElementItem, newItem: ElementItem): Boolean {
                return oldItem == newItem
            }
        }
    }

}