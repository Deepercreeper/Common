package org.deepercreeper.common.interfaces;

import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface ExFunction<T, R> {

    R apply(T t) throws Exception;

    @NotNull
    default <V> ExFunction<V, R> compose(@NotNull ExFunction<? super V, ? extends T> before) {
        return (V v) -> apply(before.apply(v));
    }

    @NotNull
    default <V> ExFunction<T, V> andThen(@NotNull ExFunction<? super R, ? extends V> after) {
        return (T t) -> after.apply(apply(t));
    }
}
