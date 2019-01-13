package org.deepercreeper.common.cache;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

public class EmptyCache<K, V> implements Cache<K, V> {
    private final Iterator<V> iterator = new Iterator<V>() {
        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public V next() {
            return null;
        }

        @Override
        public void remove() {}
    };

    @Override
    public boolean contains(@NotNull K key) {
        return false;
    }

    @Override
    public boolean containsAll(@NotNull Collection<K> keys) {
        return false;
    }

    @Override
    public boolean containsAll(@NotNull K[] keys) {
        return false;
    }

    @NotNull
    @Override
    public Optional<V> get(@NotNull K key) {
        return Optional.empty();
    }

    @Override
    public void put(@NotNull K key, @NotNull V item) {}

    @Override
    public void putAll(@NotNull Map<K, V> map) {}

    @Override
    public void remove(@NotNull K key) {}

    @Override
    public void clear() {}

    @NotNull
    @Override
    public Iterator<V> iterator() {
        return iterator;
    }
}
