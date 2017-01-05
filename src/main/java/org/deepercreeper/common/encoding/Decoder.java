package org.deepercreeper.common.encoding;

public interface Decoder<T extends Encodable>
{
    T decode(String value);
}
