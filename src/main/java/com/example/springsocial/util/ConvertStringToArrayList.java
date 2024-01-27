package com.example.springsocial.util;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
@Repository
public class ConvertStringToArrayList {
    public static ArrayList<String> convertToArrayList(String input) {
        if (input == null) {
            return null;
        } else {
            // Remove the curly braces and quotes
            String cleanedInput = input.replaceAll("[{}']", "");

            // Split the string by comma and trim whitespace
            String[] elements = cleanedInput.split(",");
            ArrayList<String> resultList = new ArrayList<>();

            // Add each element to the ArrayList
            for (String element : elements) {
                resultList.add(element.trim());
            }

            return resultList;
        }
    }
}
