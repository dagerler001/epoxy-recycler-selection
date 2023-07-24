package com.vidmind.selection.sample.simple;

import static androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP;
import static androidx.annotation.VisibleForTesting.PACKAGE_PRIVATE;
import static androidx.core.util.Preconditions.checkArgument;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.recyclerview.selection.ItemKeyProvider;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Provides the necessary glue to notify RecyclerView when selection data changes,
 * and to notify SelectionTracker when the underlying RecyclerView.Adapter data changes.
 * <p>
 * This strict decoupling is necessary to permit a single SelectionTracker to work
 * with multiple RecyclerView instances. This may be necessary when multiple
 * different views of data are presented to the user.
 *
 * @hide
 */
@RestrictTo(LIBRARY_GROUP)
@VisibleForTesting(otherwise = PACKAGE_PRIVATE)
public class SimpleEventBridge<K> implements BridgeSelector<K> {

    private static final String TAG = "EventsRelays";



    @Override
    public void install(@NonNull RecyclerView.Adapter<?> adapter,
                        @NonNull SelectionTracker<K> selectionTracker,
                        @NonNull ItemKeyProvider<K> keyProvider) {
        // setup bridges to relay selection and adapter events
        new TrackerToAdapterBridge<>(selectionTracker, keyProvider, adapter);
        //adapter.registerAdapterDataObserver(selectionTracker.getAdapterDataObserver());
    }

    private static final class TrackerToAdapterBridge<K>
        extends SelectionTracker.SelectionObserver<K> {

        private final ItemKeyProvider<K> mKeyProvider;
        private final RecyclerView.Adapter<?> mAdapter;

        TrackerToAdapterBridge(
            @NonNull SelectionTracker<K> selectionTracker,
            @NonNull ItemKeyProvider<K> keyProvider,
            @NonNull RecyclerView.Adapter<?> adapter) {

            selectionTracker.addObserver(this);

            checkArgument(keyProvider != null);
            checkArgument(adapter != null);

            mKeyProvider = keyProvider;
            mAdapter = adapter;
        }

        /**
         * Called when state of an item has been changed.
         */
        @Override
        public void onItemStateChanged(@NonNull K key, boolean selected) {
            int position = mKeyProvider.getPosition(key);
            Log.v(TAG, "ITEM " + key + " CHANGED at pos: " + position);

            if (position < 0) {
                Log.w(TAG, "Item change notification received for unknown item: " + key);
                return;
            }

            mAdapter.notifyItemChanged(position, SelectionTracker.SELECTION_CHANGED_MARKER);
        }
    }
}

