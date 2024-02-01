package com.example.springsocial.payload;

import com.example.springsocial.model.GradeReview;

public class GradeReviewRespone {
    private boolean success;
    private int code;
    private GradeReview[] gradeReviews;

    public GradeReviewRespone(boolean success, int code, GradeReview[] gradeReviews) {
        this.success = success;
        this.code = code;
        this.gradeReviews = gradeReviews;
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

    public GradeReview[] getGradeReviews() {
        return gradeReviews;
    }

    public void setGradeReviews(GradeReview[] gradeReviews) {
        this.gradeReviews = gradeReviews;
    }
}
