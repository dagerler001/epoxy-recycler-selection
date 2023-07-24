package com.vidmind.selection.sample

import android.graphics.Color
import com.vidmind.selection.sample.epoxy.Item

object SampleData {

    val colors = arrayListOf(
        Color.BLUE,
        Color.GREEN,
        Color.GRAY,
        Color.RED,
        Color.MAGENTA,
        Color.BLUE,
        Color.GREEN,
        Color.GRAY,
        Color.RED,
        Color.MAGENTA,
        Color.BLUE,
        Color.GREEN,
        Color.GRAY,
        Color.RED,
        Color.MAGENTA,
        Color.BLUE,
        Color.GREEN,
        Color.GRAY,
        Color.RED,
        Color.MAGENTA
    )

    var items = MutableList(20) {
        Item(
            id = it.toLong(),
            title = "Title $it",
            color = colors[it]
        )
    }
}