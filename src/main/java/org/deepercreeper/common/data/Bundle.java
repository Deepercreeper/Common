package org.deepercreeper.common.data;

import org.deepercreeper.common.encoding.Decoder;
import org.deepercreeper.common.encoding.Encodable;
import org.deepercreeper.common.encoding.Encoder;
import org.deepercreeper.common.util.CodingUtil;
import org.deepercreeper.common.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Bundle implements Encodable {
    private final Map<String, String> values = new HashMap<>();

    public Bundle() {}

    private Bundle(@NotNull String value) {
        String[] keysAndValues = CodingUtil.decode(value).split(CodingUtil.DELIMITER, -1);
        for (int index = 0; index < keysAndValues.length - 1; index += 2) {
            parse(keysAndValues[index], keysAndValues[index + 1]);
        }
    }

    private void parse(@NotNull String key, @NotNull String value) {
        values.put(CodingUtil.decode(key), value);
    }

    @NotNull
    public <T extends Encodable> Bundle put(@NotNull String key, @NotNull T value) {
        values.put(key, value.encode());
        return this;
    }

    @NotNull
    public <T> Bundle put(@NotNull String key, @NotNull T value, @NotNull Encoder<T> encoder) {
        values.put(key, encoder.encode(value));
        return this;
    }

    @NotNull
    public Bundle put(@NotNull String key, int value) {
        return put(key, String.valueOf(value));
    }

    @NotNull
    public Bundle put(@NotNull String key, long value) {
        return put(key, String.valueOf(value));
    }

    @NotNull
    public Bundle put(@NotNull String key, double value) {
        return put(key, String.valueOf(value));
    }

    @NotNull
    public Bundle put(@NotNull String key, boolean value) {
        return put(key, String.valueOf(value));
    }

    @NotNull
    public Bundle put(@NotNull String key, @NotNull boolean[] value) {
        StringBuilder builder = new StringBuilder();
        for (boolean b : value) {
            builder.append(b ? '1' : '0');
        }
        return put(key, builder.toString());
    }

    @NotNull
    public Bundle put(@NotNull String key, @NotNull byte[] value) {
        StringBuilder builder = new StringBuilder();
        for (byte b : value) {
            int unsignedInt = ((int) b) & 0xff;
            String hex = unsignedInt < 16 ? "0" + Integer.toHexString(unsignedInt) : Integer.toHexString(unsignedInt);
            builder.append(hex);
        }
        return put(key, builder.toString());
    }

    @NotNull
    public Bundle put(@NotNull String key, @NotNull int[] value) {
        StringBuilder builder = new StringBuilder();
        for (int i : value) {
            String hex = StringUtil.pad(Integer.toHexString(i), "0", 8);
            builder.append(hex);
        }
        return put(key, builder.toString());
    }

    @NotNull
    public Bundle put(@NotNull String key, @NotNull String value) {
        values.put(key, CodingUtil.encode(value));
        return this;
    }

    @NotNull
    public <T extends Encodable> T get(@NotNull String key, @NotNull Decoder<T> decoder) {
        return decoder.decode(get(key));
    }

    public int getInt(@NotNull String key) {
        String value = get(key);
        try {
            return Integer.parseInt(value);
        }
        catch (NumberFormatException e) {
            throw new IllegalArgumentException("Value is no integer: " + key + " -> " + value);
        }
    }

    public long getLong(@NotNull String key) {
        String value = get(key);
        try {
            return Long.parseLong(value);
        }
        catch (NumberFormatException e) {
            throw new IllegalArgumentException("Value is no long: " + key + " -> " + value);
        }
    }

    public double getDouble(@NotNull String key) {
        String value = get(key);
        try {
            return Double.parseDouble(value);
        }
        catch (NumberFormatException e) {
            throw new IllegalArgumentException("Value is no double: " + key + " -> " + value);
        }
    }

    public boolean getBoolean(@NotNull String key) {
        String value = get(key);
        if (!value.equalsIgnoreCase("true") && !value.equalsIgnoreCase("false")) {
            throw new IllegalArgumentException("Value is no boolean: " + key + " -> " + value);
        }
        return Boolean.parseBoolean(value);
    }

    @NotNull
    public boolean[] getBooleans(@NotNull String key) {
        String value = get(key);
        boolean[] booleans = new boolean[value.length()];
        for (int i = 0; i < booleans.length; i++) {
            booleans[i] = value.charAt(i) == '1';
        }
        return booleans;
    }

    @NotNull
    public byte[] getBytes(@NotNull String key) {
        String value = get(key);
        byte[] bytes = new byte[value.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = Integer.decode("0x" + value.charAt(i * 2) + value.charAt(i * 2 + 1)).byteValue();
        }
        return bytes;
    }

    @NotNull
    public int[] getIntegers(@NotNull String key) {
        String value = get(key);
        int[] integers = new int[value.length() / 8];
        for (int i = 0; i < integers.length; i++) {
            integers[i] = (int) Long.parseLong(value.substring(i * 8, i * 8 + 8), 16);
        }
        return integers;
    }

    @NotNull
    public String getString(@NotNull String key) {
        return CodingUtil.decode(get(key));
    }

    @NotNull
    private String get(@NotNull String key) {
        return Optional.ofNullable(values.get(key)).orElseThrow(() -> new IllegalArgumentException("Unknown key: " + key));
    }

    public int size() {
        return values.size();
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }

    @NotNull
    @Override
    public String encode() {
        String[] keysAndValues = new String[2 * values.size()];
        int index = 0;
        for (Map.Entry<String, String> keyAndValue : values.entrySet()) {
            keysAndValues[index++] = CodingUtil.encode(keyAndValue.getKey());
            keysAndValues[index++] = keyAndValue.getValue();
        }
        return CodingUtil.encode(StringUtil.join(keysAndValues, CodingUtil.DELIMITER));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Bundle) {
            Bundle bundle = (Bundle) obj;
            return values.equals(bundle.values);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return values.hashCode();
    }

    @Override
    public String toString() {
        return values.toString();
    }

    @NotNull
    public static Bundle decode(@NotNull String value) {
        try {
            return new Bundle(value);
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Could not decode bundle: " + value, e);
        }
    }
}
