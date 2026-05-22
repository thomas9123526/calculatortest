package com.example.cryptolib;

import android.util.Base64;

import java.nio.charset.StandardCharsets;

public class SimpleCrypto {

    private static final byte[] KEY = "MySecretKey42".getBytes(StandardCharsets.UTF_8);

    public static String encrypt(String plain) {
        if (plain == null) return "";
        byte[] in = plain.getBytes(StandardCharsets.UTF_8);
        byte[] out = xor(in);
        return Base64.encodeToString(out, Base64.NO_WRAP);
    }

    public static String decrypt(String cipher) {
        if (cipher == null || cipher.isEmpty()) return "";
        byte[] in = Base64.decode(cipher, Base64.NO_WRAP);
        byte[] out = xor(in);
        return new String(out, StandardCharsets.UTF_8);
    }

    private static byte[] xor(byte[] data) {
        byte[] out = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            out[i] = (byte) (data[i] ^ KEY[i % KEY.length]);
        }
        return out;
    }
}
