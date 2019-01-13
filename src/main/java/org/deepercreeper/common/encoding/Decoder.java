package org.deepercreeper.common.encoding;

import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface Decoder<T extends Encodable> {
    @NotNull T decode(@NotNull String value);
}