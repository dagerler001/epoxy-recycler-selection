package com.vidmind.selection.sample.simple

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView

class SimpleItemsDetailsLookup(private val recyclerView: RecyclerView) : ItemDetailsLookup<Long>() {

    override fun getItemDetails(event: MotionEvent): ItemDetails<Long>? {
        val view = recyclerView.findChildViewUnder(event.x, event.y)
        if (view != null) {
            val vh = recyclerView.getChildViewHolder(view) as SimpleAdapter.ViewHolder
            return vh.getItem()
        }
        return null
    }
}