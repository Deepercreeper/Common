package org.deepercreeper.common.encoding;

public interface Encoder<T>
{
    String encode(T value);
}
