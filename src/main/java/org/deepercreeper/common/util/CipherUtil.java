package org.deepercreeper.common.util;

import org.apache.commons.codec.binary.Base64;
import org.deepercreeper.common.pairs.ImmutablePair;
import org.deepercreeper.common.pairs.Pair;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

public class CipherUtil
{
    private static final int ITERATIONS = 65536;

    private static final int KEY_SIZE = 256;

    private CipherUtil() {}

    public static SecretKey generateKey(String password)
    {
        try
        {
            return tryGenerateKey(password);
        }
        catch (Exception e)
        {
            throw new RuntimeException("Could not generate secret key:", e);
        }
    }

    private static SecretKey tryGenerateKey(String password) throws Exception
    {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), generateSalt(), ITERATIONS, KEY_SIZE);
        SecretKey tmp = factory.generateSecret(spec);
        return new SecretKeySpec(tmp.getEncoded(), "AES");
    }

    public static Pair<String, byte[]> encrypt(char[] plainText, SecretKey secretKey)
    {
        try
        {
            return tryEncrypt(plainText, secretKey);
        }
        catch (Exception e)
        {
            throw new RuntimeException("Could not encrypt text:", e);
        }
    }

    private static Pair<String, byte[]> tryEncrypt(char[] plainText, SecretKey secretKey) throws Exception
    {
        SecretKeySpec secretSpec = new SecretKeySpec(secretKey.getEncoded(), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretSpec);
        AlgorithmParameters params = cipher.getParameters();
        byte[] ivBytes = params.getParameterSpec(IvParameterSpec.class).getIV();
        byte[] encryptedTextBytes = cipher.doFinal(String.valueOf(plainText).getBytes(StandardCharsets.UTF_8));

        return new ImmutablePair<>(Base64.encodeBase64String(encryptedTextBytes), ivBytes);
    }

    public static String decrypt(char[] encryptedText, byte[] ivBytes, SecretKey secretKey)
    {
        try
        {
            return tryDecrypt(encryptedText, ivBytes, secretKey);
        }
        catch (Exception e)
        {
            throw new RuntimeException("Could not decrypt text:", e);
        }
    }

    private static String tryDecrypt(char[] encryptedText, byte[] ivBytes, SecretKey secretKey) throws Exception
    {
        byte[] encryptedTextBytes = Base64.decodeBase64(new String(encryptedText));
        SecretKeySpec secretSpec = new SecretKeySpec(secretKey.getEncoded(), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretSpec, new IvParameterSpec(ivBytes));
        return new String(cipher.doFinal(encryptedTextBytes), StandardCharsets.UTF_8);
    }

    private static byte[] generateSalt() throws Exception
    {
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[8];
        random.nextBytes(salt);
        return salt;
    }
}
