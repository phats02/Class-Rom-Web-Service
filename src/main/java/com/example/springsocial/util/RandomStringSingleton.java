package com.example.springsocial.util;

// File: src/main/java/com/example/springsocial/util/RandomStringSingleton.java

import net.bytebuddy.utility.RandomString;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Random;

//design pattern singleton
//design pattern singleton
@Component
public class RandomStringSingleton {
//    private static RandomStringSingleton instance;
//    private RandomString randomString;
//
//    private RandomStringSingleton(int length) {
//        randomString = new RandomString(length);
//    }
//
//    public static  RandomStringSingleton getInstance(int length) {
//        if (instance == null) {
//            instance = new RandomStringSingleton(length);
//        }
//        return instance;
//    }
//
//    public String make() {
//        return randomString.make();
//    }

    private static RandomStringSingleton instance;
    private final Random random;

    private RandomStringSingleton() {
        random = new Random();
    }

    public static RandomStringSingleton getInstance() {
        if (instance == null) {
            instance = new RandomStringSingleton();
        }
        return instance;
    }

    public String generateRandomString(int length) {
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder randomString = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            randomString.append(characters.charAt(index));
        }

        return randomString.toString();
    }
}