package org.deepercreeper.common.pairs;

import java.util.Objects;

public class ImmutablePair<K, V> implements Pair<K, V>
{
    private final K key;

    private final V value;

    private final int hashCode;

    public ImmutablePair(K key, V value)
    {
        this.key = key;
        this.value = value;
        hashCode = computeHashCode();
    }

    @Override
    public void setKey(K key)
    {
        throw new UnsupportedOperationException("Setting the key is unsupported inside immutable pairs");
    }

    @Override
    public void setValue(V value)
    {
        throw new UnsupportedOperationException("Setting the value is unsupported inside immutable pairs");
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

    private int computeHashCode()
    {
        int keyHashCode = getKey() != null ? getKey().hashCode() : 0;
        int valueHashCode = getValue() != null ? getValue().hashCode() : 0;
        return keyHashCode * 13 + valueHashCode;
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
