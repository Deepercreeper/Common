package org.deepercreeper.common.cache;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class HoldingCache<K, V> implements Cache<K, V> {
    private final Map<K, V> items = new HashMap<>();

    @Override
    public void clear() {
        items.clear();
    }

    @Override
    public boolean contains(@NotNull K key) {
        return items.containsKey(key);
    }

    @Override
    public boolean containsAll(@NotNull Collection<K> keys) {
        for (K key : keys) {
            if (!items.containsKey(key)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean containsAll(@NotNull K[] keys) {
        for (K key : keys) {
            if (!items.containsKey(key)) {
                return false;
            }
        }
        return true;
    }

    @NotNull
    @Override
    public Optional<V> get(@NotNull K key) {
        return Optional.ofNullable(items.get(key));
    }

    @Override
    public void put(@NotNull K key, @NotNull V item) {
        items.put(key, item);
    }

    @Override
    public void putAll(@NotNull Map<K, V> map) {
        items.putAll(map);
    }

    @Override
    public void remove(@NotNull K key) {
        items.remove(key);
    }

    @Override
    public String toString() {
        return items.values().toString();
    }

    @NotNull
    @Override
    public Iterator<V> iterator() {
        return items.values().iterator();
    }
}
