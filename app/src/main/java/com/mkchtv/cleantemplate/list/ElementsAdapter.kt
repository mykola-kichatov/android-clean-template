package com.mkchtv.cleantemplate.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mkchtv.cleantemplate.databinding.ListItemElementBinding

class ElementsAdapter(
    private val onItemClicked: (ElementItem) -> Unit
) : ListAdapter<ElementItem, ElementsAdapter.ElementViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElementViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemElementBinding.inflate(inflater, parent, false)
        val viewHolder = ElementViewHolder(binding)
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            onItemClicked(getItem(position))
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ElementViewHolder, position: Int) =
        holder.bind(getItem(position))

    class ElementViewHolder(
        private val binding: ListItemElementBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ElementItem) {
            binding.name.text = item.name
            binding.description.text = item.description
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