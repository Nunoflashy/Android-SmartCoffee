package com.example.projetofinal_smartcoffee.Util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hasher {
    public static String GetSHA512(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA512");
        md.update(input.getBytes());
        byte[] digest = md.digest();
        return digest.toString();
    }
}
