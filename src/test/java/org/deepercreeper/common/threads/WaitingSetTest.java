package org.deepercreeper.common.threads;

import org.deepercreeper.common.ids.Indexable;
import org.deepercreeper.common.util.Util;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

public class WaitingSetTest
{
    @Test
    public void testWait()
    {
        final WaitingSet<TestIndexable> set = new WaitingSet<>();
        final AtomicBoolean flag = new AtomicBoolean();
        new Thread()
        {
            @Override
            public void run()
            {
                set.get(0);
                flag.set(true);
            }
        }.start();

        Util.sleep(1000);
        Assert.assertFalse(flag.get());

        set.add(new TestIndexable(0));
        Util.sleep(1000);
        Assert.assertTrue(flag.get());
    }

    private static class TestIndexable implements Indexable
    {
        private final int id;

        private TestIndexable(int id) {this.id = id;}

        @Override
        public int getId()
        {
            return id;
        }
    }
}
