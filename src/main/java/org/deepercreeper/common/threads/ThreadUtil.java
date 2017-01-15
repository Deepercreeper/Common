package org.deepercreeper.common.threads;

public class ThreadUtil
{
    public static void execute(Stoppable stoppable, Stoppable.FinishListener... listeners)
    {
        if (stoppable.isFinished())
        {
            stoppable.reset();
        }
        if (listeners != null && listeners.length > 0)
        {
            for (Stoppable.FinishListener listener : listeners)
            {
                stoppable.addListener(listener);
            }
        }
        new Thread(stoppable).start();
    }
}