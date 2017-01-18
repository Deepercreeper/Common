package org.deepercreeper.common.util;

import org.deepercreeper.common.pairs.Pair;
import org.junit.Assert;
import org.junit.Test;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;

public class CipherUtilTest
{
    @Test
    public void testEncryptDecrypt()
    {
        String message = "91.59.115.70";
        String password = "UltiMathPassword";

        SecretKey secretKey = CipherUtil.generateKey(password);

        Pair<String, byte[]> encrypted = CipherUtil.encrypt(message.toCharArray(), secretKey);

        System.out.println(encrypted.getKey());
        System.out.println(Arrays.toString(encrypted.getValue()));
        System.out.println(Arrays.toString(secretKey.getEncoded()));

        Assert.assertEquals(message, CipherUtil.decrypt(encrypted.getKey().toCharArray(), encrypted.getValue(), new SecretKeySpec(secretKey.getEncoded(), password)));
    }

    @Test
    public void testSecondDecrypt()
    {
        byte[] ivBytes = new byte[]{-45, -34, -53, 104, 83, -79, -73, -6, -11, -50, 68, -84, 48, 7, -115, 56};
        byte[] encodedSecretKey = new byte[]{21, -54, -27, -70, -24, 116, -118, -102, -6, -77, -44, 86, 109, -87, 126, 112, -26, -99, -26, 37, -72, -73, 1, -95, 93, -57, -113, 60, -18, 26, -51, 76};

        SecretKey secretKey = new SecretKeySpec(encodedSecretKey, "UltiMathPassword");

        System.out.println(CipherUtil.decrypt("epLGwnBE5rBciTletbMFHg==".toCharArray(), ivBytes, secretKey));
    }
}
