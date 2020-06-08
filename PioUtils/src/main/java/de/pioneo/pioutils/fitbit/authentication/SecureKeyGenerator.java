package de.pioneo.pioutils.fitbit.authentication;

import android.os.Build;
import android.util.Base64;

import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * Created by jboggess on 9/28/16.
 */

public class SecureKeyGenerator {

    public static void main(String[] args) {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(256);
            SecretKey secretKey = keyGen.generateKey();
            System.out.println(Base64.encodeToString(secretKey.getEncoded(), Base64.NO_WRAP));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
