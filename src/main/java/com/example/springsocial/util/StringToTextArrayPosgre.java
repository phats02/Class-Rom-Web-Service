package com.example.springsocial.util;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
@Repository

public class StringToTextArrayPosgre {

    public static String convert(String[] array) {
        // Add single quotes to each element and join them with commas
        String formattedString = String.join(", ", Arrays.stream(array)
                .map(element -> "'" + element + "'")
                .toArray(String[]::new));

        // Add curly braces around the formatted string
        String result = "{" + formattedString + "}";

//        String element2 = result.substring(2, result.length() - 2);
//
//        // Add curly braces around the formatted string
//        String result2 = "{" + element2 + "}";

        return result;
    }

}
