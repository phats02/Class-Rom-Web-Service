package com.example.springsocial.payload;

import com.example.springsocial.model.GradeReviewV3;

public class GradeReviewResponeV2 {
    private boolean success;
    private int code;
    private GradeReviewV3[] gradeReviews;

    public GradeReviewResponeV2(boolean success, int code, GradeReviewV3[] gradeReviews) {
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

    public GradeReviewV3[] getGradeReviews() {
        return gradeReviews;
    }

    public void setGradeReviews(GradeReviewV3[] gradeReviews) {
        this.gradeReviews = gradeReviews;
    }
}
