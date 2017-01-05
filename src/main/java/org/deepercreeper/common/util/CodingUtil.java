package org.deepercreeper.common.util;

import org.deepercreeper.common.encoding.Decoder;
import org.deepercreeper.common.encoding.Encodable;
import org.deepercreeper.common.encoding.Encoder;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CodingUtil
{
    public static final String DELIMITER = ",";

    private CodingUtil() {}

    public static String encode(String value)
    {
        ConditionUtil.checkNotNull(value, "Value");
        try
        {
            return URLEncoder.encode(value, "UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            throw new RuntimeException("Could not encode value:", e);
        }
    }

    public static String encode(String... values)
    {
        ConditionUtil.checkNotNull(values, "Values");
        String[] encodedValues = new String[values.length];
        for (int i = 0; i < values.length; i++)
        {
            encodedValues[i] = encode(values[i]);
        }
        return encode(join(encodedValues));
    }

    public static String encode(Object... values)
    {
        ConditionUtil.checkNotNull(values, "Values");
        String[] encodedValues = new String[values.length];
        for (int i = 0; i < values.length; i++)
        {
            encodedValues[i] = encode(values[i].toString());
        }
        return encode(join(encodedValues));
    }

    public static <T extends Encodable> String encode(Collection<T> encodables)
    {
        ConditionUtil.checkNotNull(encodables, "Encodables");
        String[] encodedValues = new String[encodables.size()];
        int i = 0;
        for (T encodable : encodables)
        {
            encodedValues[i] = encodable.encode();
            i++;
        }
        return encode(join(encodedValues));
    }

    public static String encode(Encodable... encodables)
    {
        ConditionUtil.checkNotNull(encodables, "Encodables");
        String[] encodedValues = new String[encodables.length];
        for (int i = 0; i < encodables.length; i++)
        {
            encodedValues[i] = encodables[i].encode();
        }
        return encode(join(encodedValues));
    }

    public static <T> String encode(Collection<T> values, Encoder<T> encoder)
    {
        ConditionUtil.checkNotNull(values, "Values");
        ConditionUtil.checkNotNull(encoder, "Encoder");
        String[] encodedValues = new String[values.size()];
        int i = 0;
        for (T value : values)
        {
            encodedValues[i] = encoder.encode(value);
            i++;
        }
        return encode(join(encodedValues));
    }

    public static <T> String encode(T[] values, Encoder<T> encoder)
    {
        ConditionUtil.checkNotNull(values, "Values");
        ConditionUtil.checkNotNull(encoder, "Encoder");
        String[] encodedValues = new String[values.length];
        int i = 0;
        for (T value : values)
        {
            encodedValues[i] = encoder.encode(value);
            i++;
        }
        return encode(join(encodedValues));
    }

    public static String decode(String value)
    {
        ConditionUtil.checkNotNull(value, "Value");
        try
        {
            return URLDecoder.decode(value, "UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            throw new RuntimeException("Could not decode value:", e);
        }
    }

    public static <T extends Encodable> T[] decodeArray(String value, Decoder<T> decoder, ArrayFactory<T> arrayFactory)
    {
        if (value.isEmpty())
        {
            return arrayFactory.create(0);
        }
        String[] values = split(decode(value));
        T[] array = arrayFactory.create(values.length);
        for (int i = 0; i < array.length; i++)
        {
            array[i] = decoder.decode(values[i]);
        }
        return array;
    }

    public static <T extends Encodable> List<T> decodeList(String value, Decoder<T> decoder)
    {
        if (value.isEmpty())
        {
            return Collections.emptyList();
        }
        String[] values = split(decode(value));
        List<T> list = new ArrayList<>();
        for (String encodedValue : values)
        {
            list.add(decoder.decode(encodedValue));
        }
        return list;
    }

    public static String[] split(String value)
    {
        return value.split(DELIMITER, -1);
    }

    public static String join(String... values)
    {
        return StringUtil.join(values, DELIMITER);
    }

    public static String join(Object... values)
    {
        return StringUtil.join(values, DELIMITER);
    }

    public static <T> String join(Collection<T> values)
    {
        return StringUtil.join(values, DELIMITER);
    }

    public static <T> Encoder<T> toStringEncoder()
    {
        return new Encoder<T>()
        {
            @Override
            public String encode(T value)
            {
                return "" + value;
            }
        };
    }
}
