package com.vidmind.selection.sample.simple;

import androidx.annotation.NonNull;
import androidx.recyclerview.selection.ItemKeyProvider;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.widget.RecyclerView;

public interface BridgeSelector<K> {
    void install(@NonNull RecyclerView.Adapter<?> adapter,
                 @NonNull SelectionTracker<K> selectionTracker,
                 @NonNull ItemKeyProvider<K> keyProvider);
}
