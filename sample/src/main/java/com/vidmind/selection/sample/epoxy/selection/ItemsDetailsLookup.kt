package com.vidmind.selection.sample.epoxy.selection

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.epoxy.EpoxyViewHolder
import com.vidmind.selection.sample.epoxy.model.ListModel

class ItemsDetailsLookup(private val recyclerView: RecyclerView) : ItemDetailsLookup<Long>() {

    override fun getItemDetails(event: MotionEvent): ItemDetails<Long>? {
        val view = recyclerView.findChildViewUnder(event.x, event.y)
        if (view != null) {
            val vh = recyclerView.getChildViewHolder(view) as EpoxyViewHolder
            val model = vh.model as ListModel<*>
            return model.getItem(vh.bindingAdapterPosition)
        }
        return null
    }
}