package org.deepercreeper.common.threads;

import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public abstract class Stoppable implements Runnable {
    private final Set<FinishListener> listeners = new HashSet<>();

    private final String name;

    private boolean stopRequested = false;

    private boolean finished = false;

    private Thread thread;

    public Stoppable() {
        this.name = null;
    }

    public Stoppable(@NotNull String name) {
        this.name = name;
    }

    public final void reset() {
        thread = null;
        stopRequested = false;
        finished = false;
        resetInternal();
    }

    @Override
    public final void run() {
        thread = Thread.currentThread();
        setName();
        execute();
        finished();
        finished = true;
    }

    private void setName() {
        if (name != null) {
            thread.setName(name);
        }
    }

    private void finished() {
        for (FinishListener listener : listeners) {
            listener.finished(this);
        }
    }

    public final boolean isFinished() {
        return finished;
    }

    @NotNull
    public final Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    public final void addListener(@NotNull FinishListener listener) {
        listeners.add(listener);
    }

    public final void removeListener(@NotNull FinishListener listener) {
        listeners.remove(listener);
    }

    public final void stop() {
        stopRequested = true;
        stopInternal();
        if (isInterruptible() && thread != null) {
            thread.interrupt();
        }
    }

    protected void resetInternal() {}

    protected void stopInternal() {}

    protected boolean isInterruptible() {
        return true;
    }

    public final boolean isStopRequested() {
        return stopRequested;
    }

    public abstract void execute();

    @FunctionalInterface
    public interface FinishListener {
        void finished(@NotNull Stoppable stoppable);
    }
}