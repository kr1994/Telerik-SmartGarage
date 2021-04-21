package com.java.smart_garage.configuration;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class Md5Hashing {

    public static String md5(String input) {

        String md5 = null;

        if(null == input) return null;

        try {

            //Create MessageDigest object for MD5
            MessageDigest digest = MessageDigest.getInstance("MD5");

            //Update input string in message digest
            digest.update(input.getBytes(), 0, input.length());

            //Converts message digest value in base 16 (hex)
            md5 = new BigInteger(1, digest.digest()).toString(16);

        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();
        }
        return md5;
    }

    public static String generateNewPassword(int length) {
        char[] letters = {'a', 'b', 'b', 'c', '4', '1', '8', '2', 'e', '0'};
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <letters.length ; i++) {
            char current = letters[new Random().nextInt(9)];
            sb.append(current);
        }

        return sb.toString();
    }

    public static String generateNewPhoneNumber() {
        String result = "";
        for (int i = 0; i < 8; i++) {
            Random rand = new Random();
            int randomNumber = rand.nextInt(10) - 1;
            result += randomNumber + "";
        }
        return result;
    }
}
