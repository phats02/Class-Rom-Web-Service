package com.example.springsocial.payload;

import com.example.springsocial.model.GradeReview;
import com.example.springsocial.model.GradeReviewV2;

public class GradeReviewRespone {
    private boolean success;
    private int code;
    private GradeReviewV2[] gradeReviews;

    public GradeReviewRespone(boolean success, int code, GradeReviewV2[] gradeReviews) {
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

    public GradeReviewV2[] getGradeReviews() {
        return gradeReviews;
    }

    public void setGradeReviews(GradeReviewV2[] gradeReviews) {
        this.gradeReviews = gradeReviews;
    }
}
