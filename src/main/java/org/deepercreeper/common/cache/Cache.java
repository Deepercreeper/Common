package org.deepercreeper.common.cache;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public interface Cache<K, V> extends Iterable<V> {
    boolean contains(@NotNull K key);

    boolean containsAll(@NotNull Collection<K> keys);

    boolean containsAll(@NotNull K[] keys);

    @NotNull Optional<V> get(@NotNull K key);

    void put(@NotNull K key, @NotNull V item);

    void putAll(@NotNull Map<K, V> map);

    void remove(@NotNull K key);

    void clear();
}
