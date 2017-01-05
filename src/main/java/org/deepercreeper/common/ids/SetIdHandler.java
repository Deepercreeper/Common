package org.deepercreeper.common.ids;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class SetIdHandler implements IdHandler
{
    private final SortedSet<Integer> ids = new TreeSet<>();

    private int counter = 0;

    @Override
    public int generate()
    {
        synchronized (ids)
        {
            while (ids.contains(counter))
            {
                counter++;
            }
            int id = counter++;
            ids.add(id);
            return id;
        }
    }

    @Override
    public void claim(int id)
    {
        synchronized (ids)
        {
            ids.add(id);
        }
    }

    @Override
    public void claim(Set<Integer> ids)
    {
        synchronized (this.ids)
        {
            this.ids.addAll(ids);
        }
    }

    @Override
    public void release(int id)
    {
        synchronized (ids)
        {
            ids.remove(id);
            counter = id;
        }
    }

    @Override
    public Set<Integer> getIds()
    {
        Set<Integer> ids;
        synchronized (this.ids)
        {
            ids = new HashSet<>(this.ids);
        }
        return ids;
    }

    @Override
    public void reset()
    {
        synchronized (ids)
        {
            ids.clear();
            counter = 0;
        }
    }
}
