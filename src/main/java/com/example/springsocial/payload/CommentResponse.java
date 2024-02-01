package com.example.springsocial.payload;

import com.example.springsocial.model.Comment;

public class CommentResponse {
    private int code;
    private boolean success;
    private String message;
    private Comment comment;

    public CommentResponse(int code, boolean success, String message, Comment comment) {
        this.code = code;
        this.success = success;
        this.message = message;
        this.comment = comment;
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

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
}
