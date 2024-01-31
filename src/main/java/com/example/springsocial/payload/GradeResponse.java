package com.example.springsocial.payload;

import com.example.springsocial.model.Grade;
import com.example.springsocial.model.GradeV2;

public class GradeResponse {
    private int code;
    private boolean success;
    private String message;
    private GradeV2[] grade;


    public GradeResponse(int code, boolean success, String message, GradeV2[] grade) {
        this.code = code;
        this.success = success;
        this.message = message;
        this.grade = grade;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public GradeV2[] getGrade() {
        return grade;
    }

    public void setGrade(GradeV2[] grade) {
        this.grade = grade;
    }
}
