package androidx.recyclerview.selection;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public interface BridgeSelector<K> {
    void install(@NonNull RecyclerView.Adapter<?> adapter,
                 @NonNull SelectionTracker<K> selectionTracker,
                 @NonNull ItemKeyProvider<K> keyProvider);
}
