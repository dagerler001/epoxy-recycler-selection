package com.vidmind.selection.sample.epoxy.selection

import android.util.Log
import androidx.recyclerview.selection.ItemKeyProvider
import com.airbnb.epoxy.EpoxyController

class ItemsKeyProvider(private val controller: EpoxyController) :
    ItemKeyProvider<Long>(SCOPE_CACHED) {

    override fun getKey(position: Int): Long {
        val model = controller.adapter.getModelAtPosition(position)
        Log.v("ItemsKeyProvider", "ItemsKeyProvider: getKey: $position = ${model.id()}")
        return model.id()
    }

    override fun getPosition(key: Long): Int {
        Log.v("ItemsKeyProvider", "ItemsKeyProvider: getPosition: $key")
        return 0
    }

}