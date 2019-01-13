package org.deepercreeper.common.util;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class StringUtil {
    @NotNull
    public static String inject(@NotNull String message, @NotNull Object... arguments) {
        String[] stringArguments = new String[arguments.length];
        for (int i = 0; i < arguments.length; i++) {
            stringArguments[i] = arguments[i].toString();
        }
        return inject(message, stringArguments);
    }

    @NotNull
    public static String inject(@NotNull String message, @NotNull String... arguments) {
        String result = message;
        for (String argument : arguments) {
            result = result.replaceFirst("\\{}", argument);
        }
        return result;
    }

    @NotNull
    public static <T> String join(@NotNull Collection<T> values, @NotNull String delimiter) {
        String[] strings = new String[values.size()];
        int i = 0;
        for (T value : values) {
            strings[i] = value != null ? value.toString() : null;
            i++;
        }
        return join(strings, delimiter);
    }

    @NotNull
    public static <T> String join(@NotNull T[] values, @NotNull String delimiter) {
        String[] strings = new String[values.length];
        for (int i = 0; i < strings.length; i++) {
            strings[i] = String.valueOf(values[i]);
        }
        return join(strings, delimiter);
    }

    @NotNull
    public static String join(@NotNull String[] values, @NotNull String delimiter) {
        StringBuilder join = new StringBuilder();
        boolean first = true;
        for (String value : values) {
            if (first) {
                first = false;
            }
            else {
                join.append(delimiter);
            }
            join.append(value);
        }
        return join.toString();
    }

    @NotNull
    public static String pad(@NotNull String value, @NotNull String padding, int length) {
        StringBuilder paddedValue = new StringBuilder(value);
        while (paddedValue.length() < length) {
            paddedValue.insert(0, padding);
        }
        return paddedValue.toString();
    }
}
