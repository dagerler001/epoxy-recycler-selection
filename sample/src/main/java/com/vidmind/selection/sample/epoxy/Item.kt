package com.vidmind.selection.sample.epoxy

import androidx.annotation.ColorInt

data class Item(
    val id: Long,
    val title: String,
    @ColorInt val color: Int
)