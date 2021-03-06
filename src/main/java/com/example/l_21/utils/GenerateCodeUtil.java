package com.example.l_21.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class GenerateCodeUtil {
    private GenerateCodeUtil(){}

    public static String generateCode(){
        String code;

        try {
            SecureRandom random = SecureRandom.getInstanceStrong();

            int c = random.nextInt(9000) + 1000;

            code = String.valueOf(c);
        } catch (NoSuchAlgorithmException e){
            throw new RuntimeException(
                    "Problem with generating the random code."
            );
        }

        return code;
    }
}
