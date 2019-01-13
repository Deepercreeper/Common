package org.deepercreeper.common.interfaces;

import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface ExConsumer<T> {

    void accept(T t) throws Exception;

    @NotNull
    default ExConsumer<T> andThen(@NotNull ExConsumer<? super T> after) {
        return (T t) -> {
            accept(t);
            after.accept(t);
        };
    }
}
