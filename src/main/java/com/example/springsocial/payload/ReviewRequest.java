package com.example.springsocial.payload;

public class ReviewRequest {
    private float expectedGrade;
    private String message;

    public float getExpectedGrade() {
        return expectedGrade;
    }

    public void setExpectedGrade(float expectedGrade) {
        this.expectedGrade = expectedGrade;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
