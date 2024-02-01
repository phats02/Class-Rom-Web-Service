package com.example.springsocial.model;

import java.time.LocalDate;

public class GradeReviewV3 {
    private Long id;

    private String _id;

    private String studentId;

    private String assignmentId;

    private float expectedGrade;

    private float actualGrade;

    private String message;

    private int status;

    private Comment[] comments;

    private LocalDate createdAt;

    private LocalDate updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(String assignmentId) {
        this.assignmentId = assignmentId;
    }

    public float getExpectedGrade() {
        return expectedGrade;
    }

    public void setExpectedGrade(float expectedGrade) {
        this.expectedGrade = expectedGrade;
    }

    public float getActualGrade() {
        return actualGrade;
    }

    public void setActualGrade(float actualGrade) {
        this.actualGrade = actualGrade;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Comment[] getComments() {
        return comments;
    }

    public void setComments(Comment[] comments) {
        this.comments = comments;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }
}
