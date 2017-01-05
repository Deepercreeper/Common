package org.deepercreeper.common.cache;

import java.util.Map;

public interface Cache<K, V> extends Iterable<V>
{
    boolean contains(K key);

    V get(K key);

    void put(K key, V item);

    void putAll(Map<K, V> map);

    void remove(K key);

    void clear();
}
