package com.vidmind.selection.sample.epoxy.controller

import androidx.recyclerview.selection.SelectionTracker
import com.airbnb.epoxy.TypedEpoxyController
import com.vidmind.selection.sample.epoxy.model.ItemModel_
import com.vidmind.selection.sample.epoxy.Item

class PosterController : TypedEpoxyController<List<Item>>() {

    var tracker: SelectionTracker<Long>? = null

    override fun buildModels(data: List<Item>) {
        data.forEach {
            ItemModel_()
                .id(it.id)
                .title(it.title)
                .posterColor(it.color)
                .selectionTracker(tracker)
                .addTo(this)
        }
    }
}