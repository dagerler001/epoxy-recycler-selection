package com.vidmind.selection.sample.simple

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.selection.ItemDetailsLookup.ItemDetails
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vidmind.selection.sample.R
import com.vidmind.selection.sample.epoxy.Item

class SimpleAdapter : ListAdapter<Item, SimpleAdapter.ViewHolder>(DiffCallback()) {

    var tracker: SelectionTracker<Long>? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.item_sample, parent, false)
        return ViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(getItem(position)) {
            holder.poster.setBackgroundColor(color)
            holder.titleView.text = title

            tracker?.let {
                val isSelected = it.isSelected(id)
                holder.selection.isVisible = isSelected
            }
        }
    }

    override fun onViewRecycled(holder: ViewHolder) {
        holder.selection.isVisible = false
        holder.titleView.text =""
        holder.poster.setBackgroundColor(Color.TRANSPARENT)
        super.onViewRecycled(holder)
    }

    inner class ViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {
        val titleView by lazy { rootView.findViewById<TextView>(R.id.title) }
        val poster by lazy { rootView.findViewById<FrameLayout>(R.id.poster) }
        val selection by lazy { rootView.findViewById<ImageView>(R.id.selection) }


        fun getItem(): ItemDetails<Long> = object : ItemDetails<Long>() {

            override fun getPosition(): Int = bindingAdapterPosition

            override fun getSelectionKey(): Long = getItem(bindingAdapterPosition).id
        }
    }


    private class DiffCallback : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }
    }
}