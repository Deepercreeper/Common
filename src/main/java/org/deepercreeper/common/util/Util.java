package org.deepercreeper.common.util;

import java.util.*;

public class Util
{
    private Util() {}

    public static void sleep(long timeout)
    {
        try
        {
            Thread.sleep(timeout);
        }
        catch (InterruptedException ignored)
        {
        }
    }

    public static int[] toArray(Collection<Integer> integers)
    {
        int[] array = new int[integers.size()];
        int index = 0;
        for (int integer : integers)
        {
            array[index++] = integer;
        }
        return array;
    }

    public static List<Integer> toList(int[] integers)
    {
        List<Integer> list = new ArrayList<>(integers.length);
        for (int integer : integers)
        {
            list.add(integer);
        }
        return list;
    }

    public static Set<Integer> toSet(int[] integers)
    {
        Set<Integer> set = new HashSet<>();
        for (int integer : integers)
        {
            set.add(integer);
        }
        return set;
    }
}
