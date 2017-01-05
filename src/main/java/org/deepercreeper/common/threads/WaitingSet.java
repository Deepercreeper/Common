package org.deepercreeper.common.threads;

import org.deepercreeper.common.ids.IdHandler;
import org.deepercreeper.common.ids.Indexable;
import org.deepercreeper.common.ids.SetIdHandler;
import org.deepercreeper.common.util.Util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WaitingSet<T extends Indexable>
{
    private final Object lock = new Object();

    private final Map<Integer, T> map = new HashMap<>();

    private final IdHandler threadIds = new SetIdHandler();

    private final Set<Integer> dirtyThreads = new HashSet<>();

    public void add(T item)
    {
        synchronized (lock)
        {
            map.put(item.getId(), item);
            dirtyThreads.addAll(threadIds.getIds());
        }
    }

    public T get(int id)
    {
        return await(id);
    }

    public T pop(int id)
    {
        T item = await(id);
        remove(id);
        return item;
    }

    private void remove(int id)
    {
        synchronized (lock)
        {
            map.remove(id);
        }
    }

    private T await(int id)
    {
        int threadId = generateThreadId();
        while (true)
        {
            if (checkDirty(threadId))
            {
                T item = getAndRelease(id, threadId);
                if (item != null)
                {
                    return item;
                }
            }
            Util.sleep(100);
        }
    }

    private int generateThreadId()
    {
        int threadId = threadIds.generate();
        markAsDirty(threadId);
        return threadId;
    }

    private void markAsDirty(int threadId)
    {
        synchronized (lock)
        {
            dirtyThreads.add(threadId);
        }
    }

    private boolean checkDirty(int threadId)
    {
        synchronized (lock)
        {
            return dirtyThreads.remove(threadId);
        }
    }

    private T getAndRelease(int id, int threadId)
    {
        synchronized (lock)
        {
            T item = map.get(id);
            if (item != null)
            {
                threadIds.release(threadId);
            }
            return item;
        }
    }
}
