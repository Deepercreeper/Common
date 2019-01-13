package org.deepercreeper.common.interfaces;

import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface ExBiFunction<T, U, R> {
    R apply(T t, U u) throws Exception;

    @NotNull
    default <V> ExBiFunction<T, U, V> andThen(@NotNull ExFunction<? super R, ? extends V> after) {
        return (T t, U u) -> after.apply(apply(t, u));
    }
}
