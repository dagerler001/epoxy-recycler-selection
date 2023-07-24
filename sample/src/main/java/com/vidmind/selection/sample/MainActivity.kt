package com.vidmind.selection.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.view.ActionMode
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.GridLayoutManager

import com.airbnb.epoxy.EpoxyItemSpacingDecorator
import com.airbnb.epoxy.EpoxyRecyclerView
import com.vidmind.selection.sample.simple.SimpleAdapter
import com.vidmind.selection.sample.epoxy.controller.PosterController
import com.vidmind.selection.sample.epoxy.selection.ItemsDetailsLookup
import com.vidmind.selection.sample.epoxy.selection.ItemsKeyProvider
import com.vidmind.selection.sample.simple.SimpleItemsDetailsLookup
import com.vidmind.selection.sample.simple.SimpleItemsKeyProvider

class MainActivity : AppCompatActivity(), ActionMode.Callback {


    lateinit var recyclerView: EpoxyRecyclerView
    lateinit var adapter: SimpleAdapter
    lateinit var tracker: SelectionTracker<Long>


    var actionMode: ActionMode? = null
    val postersController by lazy { createPosterController() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        setupEpoxyRecycler()
        setupEpoxySelection()


        /*setupSimpleRecyler()
        setupSimpleSelection()*/
    }

    private fun setupEpoxyRecycler() {
        recyclerView = findViewById(R.id.epoxyRecyclerView)
        postersController.spanCount = 3
        val layoutManager = GridLayoutManager(this, 3).apply {
            spanSizeLookup = postersController.spanSizeLookup
        }
        with(recyclerView) {
            adapter = postersController.adapter
            addItemDecoration(EpoxyItemSpacingDecorator(12))
            setLayoutManager(layoutManager)
        }
    }

    private fun setupSimpleRecycler() {
        recyclerView = findViewById(R.id.epoxyRecyclerView)
        val layoutManager = GridLayoutManager(this, 2).apply {
            spanSizeLookup = postersController.spanSizeLookup
        }
        adapter = SimpleAdapter()
        with(recyclerView) {
            addItemDecoration(EpoxyItemSpacingDecorator(12))
            setLayoutManager(layoutManager)
        }
        recyclerView.adapter = this.adapter
        adapter.submitList(SampleData.items)
    }

    private fun setupSimpleSelection() {
        tracker = SelectionTracker.Builder(
            "selectionItem",
            recyclerView,
            SimpleItemsKeyProvider(recyclerView),
            SimpleItemsDetailsLookup(recyclerView),
            StorageStrategy.createLongStorage(),
            // SimpleEventBridge()
        ).withSelectionPredicate(
            SelectionPredicates.createSelectAnything()
        ).build()


        tracker.addObserver(object : SelectionTracker.SelectionObserver<Long>() {
            override fun onSelectionChanged() {
                super.onSelectionChanged()
                if (actionMode == null) {
                    actionMode = startSupportActionMode(this@MainActivity)
                }

                val items = tracker.selection.size()
                if (items > 0) {
                    actionMode?.title = getString(R.string.action_selected, items)
                } else {
                    actionMode?.finish()
                }
            }
        })


        adapter.tracker = tracker
    }

    private fun createPosterController(): PosterController {
        return PosterController()
    }

    private fun setupEpoxySelection() {
        tracker = SelectionTracker.Builder(
            "selectionItem",
            recyclerView,
            ItemsKeyProvider(postersController),
            ItemsDetailsLookup(recyclerView),
            StorageStrategy.createLongStorage(),
            // EpoxyBridgeSelector(postersController)
        ).withSelectionPredicate(
            SelectionPredicates.createSelectAnything()
        ).build()

        tracker.addObserver(object : SelectionTracker.SelectionObserver<Long>() {
            override fun onSelectionChanged() {
                super.onSelectionChanged()
                if (actionMode == null) {
                    actionMode = startSupportActionMode(this@MainActivity)
                }
                val items = tracker.selection.size()
                if (items > 0) {
                    actionMode?.title = getString(R.string.action_selected, items)
                } else {
                    // actionMode?.finish()
                }
            }
        })

        postersController.tracker = tracker
        postersController.setData(SampleData.items)
    }


    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        mode?.menuInflater?.inflate(R.menu.menu_actions, menu)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?) = true

    override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_select_all -> {
                val items = postersController.currentData ?: return false
                val selectAll = tracker.selection.size() < items.size
                item.setIcon(if (!selectAll) R.drawable.ic_cart_enabled else R.drawable.ic_cart_disabled)
                postersController.currentData?.map { it.id }?.let {
                    tracker.setItemsSelected(it, selectAll)
                }
            }

            R.id.action_delete -> {
                removeEpoxySelectedItem()
                return true
                val mainAdapter = recyclerView.adapter as SimpleAdapter

                val selected = mainAdapter.currentList.filter {
                    tracker.selection.contains(it.id)
                }.toMutableList()

                val items = mainAdapter.currentList.toMutableList()
                items.removeAll(selected)

                adapter.submitList(items)
                tracker.clearSelection()
                actionMode?.finish()
            }
        }
        return true
    }

    private fun removeEpoxySelectedItem() {
        val items = postersController.currentData?.toMutableList() ?: return
        val selected = items.filter {
            tracker.selection.contains(it.id)
        }

        items.removeAll(selected)
        postersController.setData(items)

        tracker.clearSelection()
        actionMode?.finish()
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        tracker.clearSelection()
        actionMode = null
    }
}