package org.deepercreeper.common.encoding;

import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface Encoder<T> {
    @NotNull String encode(@NotNull T value);

    @NotNull
    static <T> Encoder<T> toStringEncoder() {
        return item -> "" + item;
    }
}
