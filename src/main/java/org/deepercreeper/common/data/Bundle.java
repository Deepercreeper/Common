package org.deepercreeper.common.data;

import org.deepercreeper.common.encoding.Decoder;
import org.deepercreeper.common.encoding.Encodable;
import org.deepercreeper.common.encoding.Encoder;
import org.deepercreeper.common.util.CodingUtil;
import org.deepercreeper.common.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

public class Bundle implements Encodable
{
    private final Map<String, String> values = new HashMap<>();

    public Bundle() {}

    private Bundle(String value)
    {
        String[] keysAndValues = CodingUtil.decode(value).split(CodingUtil.DELIMITER, -1);
        for (int index = 0; index < keysAndValues.length - 1; index += 2)
        {
            parse(keysAndValues[index], keysAndValues[index + 1]);
        }
    }

    private void parse(String key, String value)
    {
        values.put(CodingUtil.decode(key), value);
    }

    public <T extends Encodable> Bundle put(String key, T value)
    {
        values.put(key, value.encode());
        return this;
    }

    public <T> Bundle put(String key, T value, Encoder<T> encoder)
    {
        values.put(key, encoder.encode(value));
        return this;
    }

    public Bundle put(String key, int value)
    {
        put(key, String.valueOf(value));
        return this;
    }

    public Bundle put(String key, long value)
    {
        put(key, String.valueOf(value));
        return this;
    }

    public Bundle put(String key, double value)
    {
        put(key, String.valueOf(value));
        return this;
    }

    public Bundle put(String key, boolean value)
    {
        put(key, String.valueOf(value));
        return this;
    }

    public Bundle put(String key, boolean[] value)
    {
        StringBuilder builder = new StringBuilder();
        for (boolean b : value)
        {
            builder.append(b ? '1' : '0');
        }
        put(key, builder.toString());
        return this;
    }

    public Bundle put(String key, byte[] value)
    {
        StringBuilder builder = new StringBuilder();
        for (byte b : value)
        {
            int unsignedInt = ((int) b) & 0xff;
            String hex = unsignedInt < 16 ? "0" + Integer.toHexString(unsignedInt) : Integer.toHexString(unsignedInt);
            builder.append(hex);
        }
        put(key, builder.toString());
        return this;
    }

    public Bundle put(String key, int[] value)
    {
        StringBuilder builder = new StringBuilder();
        for (int i : value)
        {
            String hex = StringUtil.pad(Integer.toHexString(i), "0", 8);
            builder.append(hex);
        }
        put(key, builder.toString());
        return this;
    }

    public Bundle put(String key, String value)
    {
        values.put(key, CodingUtil.encode(value));
        return this;
    }

    public <T extends Encodable> T get(String key, Decoder<T> decoder)
    {
        return decoder.decode(get(key));
    }

    public int getInt(String key)
    {
        String value = get(key);
        try
        {
            return Integer.parseInt(value);
        }
        catch (NumberFormatException e)
        {
            throw new IllegalArgumentException("Key is no integer: " + key + " -> " + value);
        }
    }

    public long getLong(String key)
    {
        String value = get(key);
        try
        {
            return Long.parseLong(value);
        }
        catch (NumberFormatException e)
        {
            throw new IllegalArgumentException("Key is no long: " + key + " -> " + value);
        }
    }

    public double getDouble(String key)
    {
        String value = get(key);
        try
        {
            return Double.parseDouble(value);
        }
        catch (NumberFormatException e)
        {
            throw new IllegalArgumentException("Key is no double: " + key + " -> " + value);
        }
    }

    public boolean getBoolean(String key)
    {
        String value = get(key);
        try
        {
            return Boolean.parseBoolean(value);
        }
        catch (NumberFormatException e)
        {
            throw new IllegalArgumentException("Key is no boolean: " + key + " -> " + value);
        }
    }

    public boolean[] getBooleans(String key)
    {
        String value = get(key);
        boolean[] booleans = new boolean[value.length()];
        for (int i = 0; i < booleans.length; i++)
        {
            booleans[i] = value.charAt(i) == '1';
        }
        return booleans;
    }

    public byte[] getBytes(String key)
    {
        String value = get(key);
        byte[] bytes = new byte[value.length() / 2];
        for (int i = 0; i < bytes.length; i++)
        {
            bytes[i] = Integer.decode("0x" + value.charAt(i * 2) + value.charAt(i * 2 + 1)).byteValue();
        }
        return bytes;
    }

    public int[] getIntegers(String key)
    {
        String value = get(key);
        int[] integers = new int[value.length() / 8];
        for (int i = 0; i < integers.length; i++)
        {
            integers[i] = (int) Long.parseLong(value.substring(i * 8, i * 8 + 8), 16);
        }
        return integers;
    }

    public String getString(String key)
    {
        return CodingUtil.decode(get(key));
    }

    private String get(String key)
    {
        checkKey(key);
        return values.get(key);
    }

    private void checkKey(String key)
    {
        if (!values.containsKey(key))
        {
            throw new IllegalArgumentException("Unknown key: " + key);
        }
    }

    public int size()
    {
        return values.size();
    }

    public boolean isEmpty()
    {
        return values.isEmpty();
    }

    @Override
    public String encode()
    {
        String[] keysAndValues = new String[2 * values.size()];
        int index = 0;
        for (Map.Entry<String, String> keyAndValue : values.entrySet())
        {
            keysAndValues[index++] = CodingUtil.encode(keyAndValue.getKey());
            keysAndValues[index++] = keyAndValue.getValue();
        }
        return CodingUtil.encode(StringUtil.join(keysAndValues, CodingUtil.DELIMITER));
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Bundle)
        {
            Bundle bundle = (Bundle) obj;
            return values.equals(bundle.values);
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        return values.hashCode();
    }

    @Override
    public String toString()
    {
        return values.toString();
    }

    public static Bundle decode(String value)
    {
        try
        {
            return new Bundle(value);
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException("Could not decode bundle: " + value, e);
        }
    }
}
