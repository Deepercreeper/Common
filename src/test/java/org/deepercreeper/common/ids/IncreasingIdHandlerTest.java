package org.deepercreeper.common.ids;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class IncreasingIdHandlerTest
{
    @Test
    public void testIdentities()
    {
        IncreasingIdHandler increasingIdHandler = new IncreasingIdHandler();
        Set<Integer> ids = new HashSet<>();

        for (int idsCount = 0; idsCount < 1000; idsCount++)
        {
            Assert.assertEquals(ids.size(), idsCount);
            ids.add(increasingIdHandler.generate());
        }
    }
}
