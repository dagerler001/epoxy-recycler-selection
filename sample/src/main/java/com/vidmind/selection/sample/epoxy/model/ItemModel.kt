package com.vidmind.selection.sample.epoxy.model

import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.selection.SelectionTracker
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.vidmind.selection.sample.R
import com.vidmind.selection.sample.epoxy.KotlinEpoxyHolder


@EpoxyModelClass
abstract class ItemModel : ListModel<ItemModel.ViewHolder>() {

    @EpoxyAttribute var uuid: String = ""

    @EpoxyAttribute var title: String? = ""

    @EpoxyAttribute var posterColor: Int = R.color.black

    @EpoxyAttribute var selectionTracker: SelectionTracker<Long>? = null

    private var observer: SelectionTracker.SelectionObserver<Long>? = null


    override fun getDefaultLayout() = R.layout.item_sample


    override fun bind(holder: ViewHolder) {
        with(holder) {
            titleView.text = title
            poster.setBackgroundColor(posterColor)
            setupSelection()
        }
        observer = object : SelectionTracker.SelectionObserver<Long>() {
            override fun onSelectionChanged() {
                updateSelection(holder)
            }
        }
        observer?.let { selectionTracker?.addObserver(it) }

    }

    override fun unbind(holder: ViewHolder) {
        observer?.let { selectionTracker?.removeObserver(it) }
    }

    private fun updateSelection(holder: ViewHolder) {
        holder.setupSelection()
    }

    private fun isSelected(): Boolean {
        return selectionTracker?.isSelected(id()) == true
    }

    private fun ViewHolder.setupSelection() {
        val selected = selectionTracker?.isSelected(id()) == true
        selection.isVisible = selected
    }

    class ViewHolder : KotlinEpoxyHolder() {
        val titleView by bind<TextView>(R.id.title)
        val poster by bind<FrameLayout>(R.id.poster)
        val selection by bind<ImageView>(R.id.selection)
    }
}