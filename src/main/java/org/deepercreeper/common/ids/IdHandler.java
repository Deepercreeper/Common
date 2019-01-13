package org.deepercreeper.common.ids;

import org.jetbrains.annotations.NotNull;

import java.util.Set;

public interface IdHandler {
    int generate();

    void claim(int id);

    void claim(@NotNull Set<Integer> ids);

    void release(int id);

    @NotNull Set<Integer> getIds();

    void reset();
}
