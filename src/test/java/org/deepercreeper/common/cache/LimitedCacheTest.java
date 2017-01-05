package org.deepercreeper.common.cache;

import org.junit.Assert;
import org.junit.Test;

public class LimitedCacheTest
{
    @Test
    public void testPut()
    {
        Cache<Integer, String> cache = new LimitedCache<>();

        cache.put(0, "0");
        cache.put(1, "1");

        Assert.assertEquals("0", cache.get(0));
        Assert.assertEquals("1", cache.get(1));
    }

    @Test
    public void testLimit()
    {
        int size = 5;

        Cache<Integer, String> cache = new LimitedCache<>(size);

        for (int i = 0; i < size; i++)
        {
            cache.put(i, "" + i);
        }

        Assert.assertTrue(cache.contains(0));
        Assert.assertTrue(cache.contains(1));

        cache.put(size, "" + size);

        Assert.assertFalse(cache.contains(0));
        Assert.assertTrue(cache.contains(1));

        cache.put(size + 1, "" + (size + 1));

        Assert.assertFalse(cache.contains(0));
        Assert.assertFalse(cache.contains(1));
    }

    @Test
    public void testRepeat()
    {
        int size = 5;

        Cache<Integer, String> cache = new LimitedCache<>(size);

        for (int i = 0; i < size; i++)
        {
            cache.put(i, "" + i);
        }

        cache.put(0, "0");
        cache.put(1, "1");

        for (int i = 0; i < size; i++)
        {
            Assert.assertTrue(cache.contains(i));
        }
    }

    @Test
    public void testRoll()
    {
        int size = 5;

        Cache<Integer, String> cache = new LimitedCache<>(size);

        for (int i = 0; i < size; i++)
        {
            cache.put(i, "" + i);
        }

        cache.put(0, "0");
        cache.put(size, "" + size);

        Assert.assertTrue(cache.contains(0));
        Assert.assertFalse(cache.contains(1));
    }
}
