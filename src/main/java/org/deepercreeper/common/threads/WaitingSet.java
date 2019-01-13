package org.deepercreeper.common.threads;

import org.deepercreeper.common.ids.IdHandler;
import org.deepercreeper.common.ids.Indexable;
import org.deepercreeper.common.ids.SetIdHandler;
import org.deepercreeper.common.util.Util;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class WaitingSet<T extends Indexable> {
    private final Object lock = new Object();

    private final Map<Integer, T> map = new HashMap<>();

    private final IdHandler threadIds = new SetIdHandler();

    private final Set<Integer> dirtyThreads = new HashSet<>();

    public void add(@NotNull T item) {
        synchronized (lock) {
            map.put(item.getId(), item);
            dirtyThreads.addAll(threadIds.getIds());
        }
    }

    @NotNull
    public T get(int id) {
        return await(id);
    }

    @NotNull
    public T pop(int id) {
        T item = await(id);
        remove(id);
        return item;
    }

    private void remove(int id) {
        synchronized (lock) {
            map.remove(id);
        }
    }

    @NotNull
    private T await(int id) {
        int threadId = generateThreadId();
        while (true) {
            if (checkDirty(threadId)) {
                Optional<T> item = getAndRelease(id, threadId);
                if (item.isPresent()) {
                    return item.get();
                }
            }
            Util.sleep(100);
        }
    }

    private int generateThreadId() {
        int threadId = threadIds.generate();
        markAsDirty(threadId);
        return threadId;
    }

    private void markAsDirty(int threadId) {
        synchronized (lock) {
            dirtyThreads.add(threadId);
        }
    }

    private boolean checkDirty(int threadId) {
        synchronized (lock) {
            return dirtyThreads.remove(threadId);
        }
    }

    @NotNull
    private Optional<T> getAndRelease(int id, int threadId) {
        synchronized (lock) {
            T item = map.get(id);
            if (item != null) {
                threadIds.release(threadId);
            }
            return Optional.ofNullable(item);
        }
    }
}
