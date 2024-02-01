package com.example.springsocial.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "gradereviews", uniqueConstraints = {
        @UniqueConstraint(columnNames = "_id")
})
public class GradeReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "_id")
    private String _id;
    @Column(name="student_id")
    private String studentId;
    @Column(name="assignment_id")
    private String assignmentId;
    @Column(name="expected_grade")
    private float expectedGrade;
    @Column(name="actual_grade")
    private float actualGrade;
    @Column(name="message")
    private String message;
    @Column(name="status")
    private int status;
    @Column(name="comment_id")
    private String comments;
    @Column(name="created_at")
    private LocalDate createdAt;
    @Column(name="updated_at")
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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
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
