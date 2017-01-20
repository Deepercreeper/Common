package org.deepercreeper.common.cache;

import java.util.*;

public class LimitedCache<K, V> implements Cache<K, V>
{
    private static final int DEFAULT_SIZE = 1024;

    private final int maxSize;

    private final List<K> keys;

    private final Map<K, V> map = new HashMap<>();

    public LimitedCache()
    {
        this(DEFAULT_SIZE);
    }

    public LimitedCache(int maxSize)
    {
        this.maxSize = maxSize;
        keys = new LinkedList<>();
    }

    @Override
    public void clear()
    {
        keys.clear();
        map.clear();
    }

    @Override
    public boolean contains(K key)
    {
        return keys.contains(key);
    }

    @Override
    public boolean containsAll(Collection<K> keys)
    {
        for (K key : keys)
        {
            if (!map.containsKey(key))
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean containsAll(K[] keys)
    {
        for (K key : keys)
        {
            if (!map.containsKey(key))
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public V get(K key)
    {
        return map.get(key);
    }

    @Override
    public void put(K key, V item)
    {
        if (contains(key))
        {
            keys.remove(key);
            keys.add(0, key);
            map.put(key, item);
            return;
        }
        if (map.size() == maxSize)
        {
            map.remove(keys.get(maxSize - 1));
            keys.remove(maxSize - 1);
        }
        keys.add(0, key);
        map.put(key, item);
    }

    @Override
    public void putAll(Map<K, V> map)
    {
        Map<K, V> newEntries = new HashMap<>(map);
        newEntries.keySet().removeAll(keys);
        this.map.putAll(newEntries);
        int size = newEntries.size();
        int itemsToRemove = keys.size() + size - this.maxSize;
        if (itemsToRemove > 0)
        {
            keys.removeAll(keys.subList(keys.size() - itemsToRemove, keys.size()));
        }
        keys.addAll(0, newEntries.keySet());
    }

    @Override
    public void remove(K key)
    {
        keys.remove(key);
        map.remove(key);
    }

    @Override
    public String toString()
    {
        return map.values().toString();
    }

    @Override
    public Iterator<V> iterator()
    {
        return map.values().iterator();
    }
}
