package com.example.springsocial.payload;

import com.example.springsocial.model.User;

public class AuthResponse {
    private String jwt;
    private int code ;
    private String message = "";
    private boolean success;
    private User user;

    public AuthResponse(int code,boolean success,User user,String jwt) {

        this.code=code;
        this.success=success;
        this.user=user;
        this.jwt = jwt;

    }

    public String getJWT() {
        return jwt;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setJWT(String jwt) {
        this.jwt = jwt;
    }


}
