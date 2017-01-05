package org.deepercreeper.common.pairs;

public interface Pair<K, V>
{
    void setKey(K key);

    void setValue(V value);

    K getKey();

    V getValue();
}
