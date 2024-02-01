package com.example.springsocial.payload;

public class ReviewRequest {
    private float expectedGrade;
    private String message;
    private float grade;
    private boolean approve;


    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    public boolean getApprove() {
        return approve;
    }

    public void setApprove(boolean approve) {
        this.approve = approve;
    }

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
