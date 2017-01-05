package org.deepercreeper.common.cache;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HoldingCache<K, V> implements Cache<K, V>
{
    private final Map<K, V> items = new HashMap<>();

    @Override
    public void clear()
    {
        items.clear();
    }

    @Override
    public boolean contains(K key)
    {
        return items.containsKey(key);
    }

    @Override
    public V get(K key)
    {
        return items.get(key);
    }

    @Override
    public void put(K key, V item)
    {
        items.put(key, item);
    }

    @Override
    public void putAll(Map<K, V> map)
    {
        items.putAll(map);
    }

    @Override
    public void remove(K key)
    {
        items.remove(key);
    }

    @Override
    public String toString()
    {
        return items.values().toString();
    }

    @Override
    public Iterator<V> iterator()
    {
        return items.values().iterator();
    }
}
