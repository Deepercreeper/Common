package org.deepercreeper.common.pairs;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ImmutablePair<K, V> implements Pair<K, V> {
    private final K key;

    private final V value;

    private final int hashCode;

    public ImmutablePair(@NotNull K key, @NotNull V value) {
        this.key = key;
        this.value = value;
        hashCode = computeHashCode();
    }

    @Override
    public void setKey(@NotNull K key) {
        throw new UnsupportedOperationException("Setting the key is unsupported inside immutable pairs");
    }

    @Override
    public void setValue(@NotNull V value) {
        throw new UnsupportedOperationException("Setting the value is unsupported inside immutable pairs");
    }

    @NotNull
    @Override
    public K getKey() {
        return key;
    }

    @NotNull
    @Override
    public V getValue() {
        return value;
    }

    private int computeHashCode() {
        return getKey().hashCode() * 13 + getValue().hashCode();
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Pair<?, ?>) {
            Pair<?, ?> pair = (Pair<?, ?>) obj;
            return Objects.equals(getKey(), pair.getKey()) && Objects.equals(getValue(), pair.getValue());
        }
        return false;
    }

    @Override
    public String toString() {
        return "(" + getKey() + ", " + getValue() + ")";
    }
}
