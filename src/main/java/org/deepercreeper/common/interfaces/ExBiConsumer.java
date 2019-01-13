package org.deepercreeper.common.interfaces;

import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface ExBiConsumer<T, U> {
    void accept(T t, U u) throws Exception;

    @NotNull
    default ExBiConsumer<T, U> andThen(@NotNull ExBiConsumer<? super T, ? super U> after) {
        return (t, u) -> {
            accept(t, u);
            after.accept(t, u);
        };
    }
}
