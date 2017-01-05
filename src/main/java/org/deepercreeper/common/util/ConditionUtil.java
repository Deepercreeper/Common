package org.deepercreeper.common.util;

public class ConditionUtil
{
    public static <T> void checkNotNull(T value, String valueDescription)
    {
        if (value == null)
        {
            throw new NullPointerException(valueDescription + " must not be null");
        }
    }
}
