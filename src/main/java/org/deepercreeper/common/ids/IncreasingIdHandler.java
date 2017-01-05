package org.deepercreeper.common.ids;

import java.util.Collections;
import java.util.Set;

public class IncreasingIdHandler implements IdHandler
{
    private int counter = 0;

    @Override
    public int generate()
    {
        return counter++;
    }

    @Override
    public void claim(int id)
    {
        while (counter <= id)
        {
            counter++;
        }
    }

    @Override
    public void claim(Set<Integer> ids)
    {
        if (ids.isEmpty())
        {
            return;
        }
        int maxId = Collections.max(ids);
        claim(maxId);
    }

    @Override
    public void release(int id)
    {
    }

    @Override
    public Set<Integer> getIds()
    {
        throw new UnsupportedOperationException("Increasing id handlers do not save used ids.");
    }

    @Override
    public void reset()
    {
        counter = 0;
    }
}
