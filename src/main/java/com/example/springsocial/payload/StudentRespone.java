package com.example.springsocial.payload;

import com.example.springsocial.model.User;

public class StudentRespone {
    private boolean success;
    private int code;
    private User user;


    public StudentRespone(boolean success, int code, User user) {
        this.success = success;
        this.code = code;
        this.user = user;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
