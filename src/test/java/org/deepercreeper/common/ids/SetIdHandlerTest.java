package org.deepercreeper.common.ids;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;

public class SetIdHandlerTest
{
    @Test
    public void testIdSet()
    {
        SetIdHandler idHandler = new SetIdHandler();

        Assert.assertTrue(idHandler.getIds().isEmpty());

        int id = idHandler.generate();

        Assert.assertEquals(Collections.singleton(id), idHandler.getIds());

        idHandler.release(id);

        Assert.assertTrue(idHandler.getIds().isEmpty());
    }
}
