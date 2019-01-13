package org.deepercreeper.common.threads;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class ThreadUtil {
    public static void execute(@NotNull Stoppable stoppable, @NotNull Stoppable.FinishListener... listeners) {
        if (stoppable.isFinished()) {
            stoppable.reset();
        }
        Arrays.stream(listeners).forEach(stoppable::addListener);
        new Thread(stoppable).start();
    }
}