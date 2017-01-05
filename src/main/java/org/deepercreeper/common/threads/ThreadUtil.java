package org.deepercreeper.common.threads;

import org.deepercreeper.common.util.Util;

import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadUtil
{
    public static void execute(Runnable runnable)
    {
        execute(runnable, -1, 100);
    }

    public static void execute(final Runnable runnable, long timeout, long precision)
    {
        final AtomicBoolean finished = new AtomicBoolean();
        new Thread()
        {
            @Override
            public void run()
            {
                runnable.run();
                finished.set(true);
            }
        }.start();
        long startTime = System.currentTimeMillis();
        while (!finished.get() && (timeout < 0 || System.currentTimeMillis() - startTime < timeout))
        {
            Util.sleep(precision);
        }
    }
}