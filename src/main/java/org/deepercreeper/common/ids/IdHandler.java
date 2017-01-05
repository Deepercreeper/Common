package org.deepercreeper.common.ids;

import java.util.Set;

public interface IdHandler
{
    int generate();

    void claim(int id);

    void claim(Set<Integer> ids);

    void release(int id);

    Set<Integer> getIds();

    void reset();
}
