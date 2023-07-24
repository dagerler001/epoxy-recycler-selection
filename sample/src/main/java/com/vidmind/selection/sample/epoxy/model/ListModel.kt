package com.vidmind.selection.sample.epoxy.model

import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.selection.ItemDetailsLookup
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.vidmind.selection.sample.epoxy.ActionEvent
import com.vidmind.selection.sample.epoxy.KotlinEpoxyHolder
import java.lang.ref.WeakReference

abstract class ListModel<HOLDER : KotlinEpoxyHolder> : EpoxyModelWithHolder<HOLDER>() {

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var onClickLiveData: WeakReference<MutableLiveData<ActionEvent>>? = null

    @EpoxyAttribute var bindingAdapterPosition = 0
    @EpoxyAttribute var idItem: String = "not_set"

    protected fun sendEvent(event: ActionEvent) = onClickLiveData?.get()?.postValue(event)


    fun getItem(adapterPosition: Int): ItemDetailsLookup.ItemDetails<Long> =

        object : ItemDetailsLookup.ItemDetails<Long>() {

            override fun getPosition(): Int = adapterPosition

            override fun getSelectionKey(): Long = id()
        }
}