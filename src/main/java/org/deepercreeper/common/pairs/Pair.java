package org.deepercreeper.common.pairs;

import org.jetbrains.annotations.NotNull;

public interface Pair<K, V> {
    void setKey(@NotNull K key);

    void setValue(@NotNull V value);

    @NotNull K getKey();

    @NotNull V getValue();
}
