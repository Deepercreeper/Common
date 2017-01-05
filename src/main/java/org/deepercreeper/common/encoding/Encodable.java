package org.deepercreeper.common.encoding;

public interface Encodable
{
    /**
     * The returned value must not contain {@link org.deepercreeper.common.util.CodingUtil#DELIMITER}.
     *
     * @return a string that can be used to reconstruct the encoded object.
     */
    String encode();
}
