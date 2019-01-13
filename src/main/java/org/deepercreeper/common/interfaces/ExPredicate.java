package org.deepercreeper.common.interfaces;

import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface ExPredicate<T> {

    boolean test(T t) throws Exception;

    @NotNull
    default ExPredicate<T> and(@NotNull ExPredicate<? super T> other) {
        return (t) -> test(t) && other.test(t);
    }

    @NotNull
    default ExPredicate<T> negate() {
        return (t) -> !test(t);
    }

    @NotNull
    default ExPredicate<T> or(@NotNull ExPredicate<? super T> other) {
        return (t) -> test(t) || other.test(t);
    }
}
