package org.deepercreeper.common.pairs;

import java.util.Objects;

public class MutablePair<K, V> implements Pair<K, V>
{
    private K key;

    private V value;

    private int hashCode;

    public MutablePair(K key, V value)
    {
        this.key = key;
        this.value = value;
        updateHashCode();
    }

    public MutablePair()
    {
        this(null, null);
    }

    @Override
    public void setKey(K key)
    {
        this.key = key;
        updateHashCode();
    }

    @Override
    public void setValue(V value)
    {
        this.value = value;
        updateHashCode();
    }

    @Override
    public K getKey()
    {
        return key;
    }

    @Override
    public V getValue()
    {
        return value;
    }

    private void updateHashCode()
    {
        int keyHashCode = key != null ? key.hashCode() : 0;
        int valueHashCode = value != null ? value.hashCode() : 0;
        hashCode = keyHashCode * 13 + valueHashCode;
    }

    @Override
    public int hashCode()
    {
        return hashCode;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Pair<?, ?>)
        {
            Pair<?, ?> pair = (Pair<?, ?>) obj;
            return Objects.equals(getKey(), pair.getKey()) && Objects.equals(getValue(), pair.getValue());
        }
        return false;
    }

    @Override
    public String toString()
    {
        return "(" + getKey() + ", " + getValue() + ")";
    }
}