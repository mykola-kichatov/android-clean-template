package com.mkchtv.cleantemplate.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mkchtv.cleantemplate.databinding.ListItemElementBinding

class ElementsAdapter(
    private val items: List<ElementItem>
) : RecyclerView.Adapter<ElementsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemElementBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(items[position])

    override fun getItemCount(): Int = items.size

    class ViewHolder(
        private val binding: ListItemElementBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ElementItem) {
            binding.name.text = item.name
            binding.description.text = item.description
        }

    }

}