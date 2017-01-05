package org.deepercreeper.common.data;

import org.deepercreeper.common.encoding.Decoder;
import org.deepercreeper.common.encoding.Encodable;
import org.deepercreeper.common.util.CodingUtil;
import org.deepercreeper.common.util.ConditionUtil;

public class TestEncodable implements Encodable
{
    private final String value;

    public TestEncodable(String value)
    {
        ConditionUtil.checkNotNull(value, "The value");
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }

    @Override
    public String encode()
    {
        return CodingUtil.encode(value);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof TestEncodable)
        {
            TestEncodable encodable = (TestEncodable) obj;
            return value.equals(encodable.value);
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        return value.hashCode();
    }

    @Override
    public String toString()
    {
        return value;
    }

    public static TestEncodable decode(String value)
    {
        return new TestEncodable(CodingUtil.decode(value));
    }

    public static Decoder<TestEncodable> decoder()
    {
        return new Decoder<TestEncodable>()
        {
            @Override
            public TestEncodable decode(String value)
            {
                return TestEncodable.decode(value);
            }
        };
    }
}
