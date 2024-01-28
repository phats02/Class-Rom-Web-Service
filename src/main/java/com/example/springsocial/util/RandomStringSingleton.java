package com.example.springsocial.util;

// File: src/main/java/com/example/springsocial/util/RandomStringSingleton.java

import net.bytebuddy.utility.RandomString;
//design pattern singleton
//design pattern singleton
public class RandomStringSingleton {
    private static RandomStringSingleton instance;
    private RandomString randomString;

    private RandomStringSingleton(int length) {
        randomString = new RandomString(length);
    }

    public static synchronized RandomStringSingleton getInstance(int length) {
        if (instance == null) {
            instance = new RandomStringSingleton(length);
        }
        return instance;
    }

    public String make() {
        return randomString.make();
    }
}