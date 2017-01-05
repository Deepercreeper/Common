package org.deepercreeper.common.cache;

import java.util.Iterator;
import java.util.Map;

public class EmptyCache<K, V> implements Cache<K, V>
{
    private final Iterator<V> iterator = new Iterator<V>()
    {
        @Override
        public boolean hasNext()
        {
            return false;
        }

        @Override
        public V next()
        {
            return null;
        }

        @Override
        public void remove()
        {
        }
    };

    @Override
    public boolean contains(K key)
    {
        return false;
    }

    @Override
    public V get(K key)
    {
        return null;
    }

    @Override
    public void put(K key, V item)
    {
    }

    @Override
    public void putAll(Map<K, V> map)
    {
    }

    @Override
    public void remove(K key)
    {
    }

    @Override
    public void clear()
    {
    }

    @Override
    public Iterator<V> iterator()
    {
        return iterator;
    }
}
