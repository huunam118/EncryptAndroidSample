package com.namnh.credential;

import android.util.Base64;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CredentialsAccessor {

    private static final String CHARSET_NAME = "UTF-8";
    // NOTE: See more CIPHER Algorithms supported by Android
    // https://developer.android.com/reference/javax/crypto/Cipher.html
    private static final String CIPHER_ALGORITHM = "AES/CFB/PKCS5Padding";

    static {
        System.loadLibrary("credentials-lib");
    }

    private static native String getSecretKey();

    /**
     * Note:
     * - Cipher.getInstance should not be called without setting the encryption mode and padding
     * - Cipher#getInstance should not be called with ECB as the cipher mode or without setting the
     * cipher mode because the default mode on android is ECB, which is insecure
     */
    public static String encrypt(String cleartext) throws Exception {
        byte[] key = getSecretKey().getBytes();

        SecretKeySpec keySpec = new SecretKeySpec(key, CIPHER_ALGORITHM);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);

        // TODO: I just use 16 first bytes from SecretKey. Replace your own 16 bytes ivSpec key here
        byte[] iv = Arrays.copyOf(key, 16);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

        byte[] inputByte = cleartext.getBytes(CHARSET_NAME);
        return new String(Base64.encode(cipher.doFinal(inputByte), Base64.DEFAULT));
    }

    public static String decrypt(String encrypted) throws Exception {
        byte[] key = getSecretKey().getBytes();

        SecretKeySpec keySpec = new SecretKeySpec(key, CIPHER_ALGORITHM);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);

        byte[] iv = Arrays.copyOf(key, 16);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

        byte[] inputByte = encrypted.getBytes(CHARSET_NAME);
        return new String(cipher.doFinal(Base64.decode(inputByte, Base64.DEFAULT)));
    }
}
