package org.deepercreeper.common.interfaces;

import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface ExBiPredicate<T, U> {

    boolean test(T t, U u) throws Exception;

    @NotNull
    default ExBiPredicate<T, U> and(@NotNull ExBiPredicate<? super T, ? super U> other) {
        return (T t, U u) -> test(t, u) && other.test(t, u);
    }

    @NotNull
    default ExBiPredicate<T, U> negate() {
        return (T t, U u) -> !test(t, u);
    }

    @NotNull
    default ExBiPredicate<T, U> or(@NotNull ExBiPredicate<? super T, ? super U> other) {
        return (T t, U u) -> test(t, u) || other.test(t, u);
    }
}
