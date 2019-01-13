package org.deepercreeper.common.pairs;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class MutablePair<K, V> implements Pair<K, V> {
    private K key;

    private V value;

    private int hashCode;

    public MutablePair(@NotNull K key, @NotNull V value) {
        this.key = key;
        this.value = value;
        updateHashCode();
    }

    @Override
    public void setKey(@NotNull K key) {
        this.key = key;
        updateHashCode();
    }

    @Override
    public void setValue(@NotNull V value) {
        this.value = value;
        updateHashCode();
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

    private void updateHashCode() {
        hashCode = getKey().hashCode() * 13 + getValue().hashCode();
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