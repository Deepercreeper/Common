package org.deepercreeper.common.util;

import org.deepercreeper.common.encoding.Decoder;
import org.deepercreeper.common.encoding.Encodable;
import org.deepercreeper.common.encoding.Encoder;
import org.jetbrains.annotations.NotNull;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class CodingUtil {
    public static final String DELIMITER = ",";

    private CodingUtil() {}

    @NotNull
    public static String encode(@NotNull String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.name());
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Could not encode value:", e);
        }
    }

    @NotNull
    public static String encode(@NotNull String... values) {
        String[] encodedValues = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            encodedValues[i] = encode(values[i]);
        }
        return encode(join(encodedValues));
    }

    @NotNull
    public static String encode(@NotNull Object... values) {
        String[] encodedValues = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            encodedValues[i] = encode(values[i].toString());
        }
        return encode(join(encodedValues));
    }

    @NotNull
    public static <T extends Encodable> String encode(@NotNull Collection<T> encodables) {
        String[] encodedValues = new String[encodables.size()];
        int i = 0;
        for (T encodable : encodables) {
            encodedValues[i] = encodable.encode();
            i++;
        }
        return encode(join(encodedValues));
    }

    @NotNull
    public static String encode(@NotNull Encodable... encodables) {
        String[] encodedValues = new String[encodables.length];
        for (int i = 0; i < encodables.length; i++) {
            encodedValues[i] = encodables[i].encode();
        }
        return encode(join(encodedValues));
    }

    @NotNull
    public static <T> String encode(@NotNull Collection<T> values, @NotNull Encoder<T> encoder) {
        String[] encodedValues = new String[values.size()];
        int i = 0;
        for (T value : values) {
            encodedValues[i] = encoder.encode(value);
            i++;
        }
        return encode(join(encodedValues));
    }

    @NotNull
    public static <T> String encode(@NotNull T[] values, @NotNull Encoder<T> encoder) {
        String[] encodedValues = new String[values.length];
        int i = 0;
        for (T value : values) {
            encodedValues[i] = encoder.encode(value);
            i++;
        }
        return encode(join(encodedValues));
    }

    @NotNull
    public static String decode(@NotNull String value) {
        try {
            return URLDecoder.decode(value, StandardCharsets.UTF_8.name());
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Could not decode value:", e);
        }
    }

    @NotNull
    public static <T extends Encodable> T[] decodeArray(@NotNull String value, @NotNull Decoder<T> decoder, @NotNull Function<Integer, T[]> arrayFactory) {
        if (value.isEmpty()) {
            return arrayFactory.apply(0);
        }
        String[] values = split(decode(value));
        T[] array = arrayFactory.apply(values.length);
        for (int i = 0; i < array.length; i++) {
            array[i] = decoder.decode(values[i]);
        }
        return array;
    }

    @NotNull
    public static <T extends Encodable> List<T> decodeList(@NotNull String value, @NotNull Decoder<T> decoder) {
        if (value.isEmpty()) {
            return Collections.emptyList();
        }
        String[] values = split(decode(value));
        List<T> list = new ArrayList<>();
        for (String encodedValue : values) {
            list.add(decoder.decode(encodedValue));
        }
        return list;
    }

    @NotNull
    public static String[] split(@NotNull String value) {
        return value.split(DELIMITER, -1);
    }

    @NotNull
    public static String join(@NotNull String... values) {
        return StringUtil.join(values, DELIMITER);
    }

    @NotNull
    public static String join(@NotNull Object... values) {
        return StringUtil.join(values, DELIMITER);
    }

    @NotNull
    public static <T> String join(@NotNull Collection<T> values) {
        return StringUtil.join(values, DELIMITER);
    }
}
