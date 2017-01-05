package org.deepercreeper.common.util;

import java.util.Collection;

public class StringUtil
{
    public static String inject(String message, Object... arguments)
    {
        String[] stringArguments = new String[arguments.length];
        for (int i = 0; i < arguments.length; i++)
        {
            stringArguments[i] = arguments[i].toString();
        }
        return inject(message, stringArguments);
    }

    public static String inject(String message, String... arguments)
    {
        String result = message;
        for (String argument : arguments)
        {
            result = result.replaceFirst("\\{\\}", argument);
        }
        return result;
    }

    public static <T> String join(Collection<T> values, String delimiter)
    {
        String[] strings = new String[values.size()];
        int i = 0;
        for (T value : values)
        {
            strings[i] = value != null ? value.toString() : null;
            i++;
        }
        return join(strings, delimiter);
    }

    public static <T> String join(T[] values, String delimiter)
    {
        String[] strings = new String[values.length];
        for (int i = 0; i < strings.length; i++)
        {
            T value = values[i];
            strings[i] = value != null ? value.toString() : null;
        }
        return join(strings, delimiter);
    }

    public static String join(String[] values, String delimiter)
    {
        StringBuilder join = new StringBuilder();
        boolean first = true;
        for (String value : values)
        {
            if (first)
            {
                first = false;
            }
            else
            {
                join.append(delimiter);
            }
            join.append(value != null ? value : "null");
        }
        return join.toString();
    }

    public static String pad(String value, String padding, int length)
    {
        StringBuilder paddedValue = new StringBuilder(value);
        while (paddedValue.length() < length)
        {
            paddedValue.insert(0, padding);
        }
        return paddedValue.toString();
    }
}
