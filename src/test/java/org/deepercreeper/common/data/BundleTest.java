package org.deepercreeper.common.data;

import org.deepercreeper.common.encoding.Encodable;
import org.junit.Assert;
import org.junit.Test;

public class BundleTest
{
    @Test
    public void testSimpleValues()
    {
        Bundle bundle = new Bundle();
        bundle.put("1", 1);
        bundle.put("2", 2L);
        bundle.put("3", 3.);
        bundle.put("4", true);

        Assert.assertEquals(1, bundle.getInt("1"));
        Assert.assertEquals(2L, bundle.getLong("2"));
        Assert.assertEquals(3., bundle.getDouble("3"), 0);
        Assert.assertEquals(true, bundle.getBoolean("4"));
    }

    @Test
    public void testStrings()
    {
        Bundle bundle = new Bundle();
        bundle.put("1", "value");
        bundle.put("2", "");

        Assert.assertEquals("value", bundle.getString("1"));
        Assert.assertEquals("", bundle.getString("2"));
    }

    @Test
    public void testEncodables()
    {
        Bundle bundle = new Bundle();
        bundle.put("1", new TestEncodable("value"));
        bundle.put("2", new TestEncodable(""));
        bundle.put("3", new TestEncodable("Not!\"§$%&/()=Encoded"));

        Assert.assertEquals(new TestEncodable("value"), bundle.get("1", TestEncodable.decoder()));
        Assert.assertEquals(new TestEncodable(""), bundle.get("2", TestEncodable.decoder()));
        Assert.assertEquals(new TestEncodable("Not!\"§$%&/()=Encoded"), bundle.get("3", TestEncodable.decoder()));
    }

    @Test(expected = NullPointerException.class)
    public void testNullString()
    {
        Bundle bundle = new Bundle();
        bundle.put("1", (String) null);
    }

    @Test(expected = NullPointerException.class)
    public void testNullEncodable()
    {
        Bundle bundle = new Bundle();
        bundle.put("1", (Encodable) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUnknownKey()
    {
        Bundle bundle = new Bundle();
        bundle.getString("1");
    }

    @Test
    public void testOverride()
    {
        Bundle bundle = new Bundle();
        bundle.put("1", "value");
        bundle.put("1", "newValue");

        Assert.assertEquals("newValue", bundle.getString("1"));
    }

    @Test
    public void testEncodingDecoding()
    {
        Bundle bundle = new Bundle();
        bundle.put("1", 1);
        bundle.put("2", 2L);
        bundle.put("3", 3.);
        bundle.put("4", true);
        bundle.put("5", "value");
        bundle.put("6", new TestEncodable("value"));

        Bundle decodedBundle = Bundle.decode(bundle.encode());

        Assert.assertEquals(bundle, decodedBundle);
    }

    @Test
    public void testKeys()
    {
        Bundle bundle = new Bundle();
        bundle.put("", 1);
        bundle.put("!\"§$%&/()=?", 2);

        Bundle decodedBundle = Bundle.decode(bundle.encode());

        Assert.assertEquals(1, decodedBundle.getInt(""));
        Assert.assertEquals(2, decodedBundle.getInt("!\"§$%&/()=?"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBadInt()
    {
        Bundle bundle = new Bundle();
        bundle.put("1", "abc");

        bundle.getInt("1");
    }

    @Test
    public void testBytes()
    {
        byte[] bytes = createByteArray();
        Bundle bundle = new Bundle();
        bundle.put("1", bytes);

        Assert.assertArrayEquals(bytes, bundle.getBytes("1"));

        Bundle decodedBundle = Bundle.decode(bundle.encode());

        Assert.assertArrayEquals(bundle.getBytes("1"), decodedBundle.getBytes("1"));
    }

    @Test
    public void testIntegers()
    {
        int[] integers = createIntArray();
        Bundle bundle = new Bundle();
        bundle.put("1", integers);

        Assert.assertArrayEquals(integers, bundle.getIntegers("1"));

        Bundle decodedBundle = Bundle.decode(bundle.encode());

        Assert.assertArrayEquals(bundle.getIntegers("1"), decodedBundle.getIntegers("1"));
    }

    @Test
    public void testBooleans()
    {
        boolean[] booleans = new boolean[]{true, false, true, false};
        Bundle bundle = new Bundle();
        bundle.put("1", booleans);

        Assert.assertArrayEquals(booleans, bundle.getBooleans("1"));

        Bundle decodedBundle = Bundle.decode(bundle.encode());

        Assert.assertArrayEquals(bundle.getBooleans("1"), decodedBundle.getBooleans("1"));
    }

    private byte[] createByteArray()
    {
        return new byte[]{
                -128, -127, -126, -3, -2, -1, 1, 2, 3, 125, 126, 127
        };
    }

    private int[] createIntArray()
    {
        return new int[]{
                Integer.MIN_VALUE, Integer.MIN_VALUE + 1, Integer.MIN_VALUE + 2, -3, -2, -1, 1, 2, 3, Integer.MAX_VALUE - 2, Integer.MAX_VALUE - 1, Integer.MAX_VALUE
        };
    }
}
