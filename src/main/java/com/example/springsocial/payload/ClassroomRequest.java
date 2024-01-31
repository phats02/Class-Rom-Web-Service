package com.example.springsocial.payload;

import javax.validation.constraints.NotBlank;

public class ClassroomRequest {

    private String name;
    private String description;

    private String[] studentIds;
    private float grade;
    private String studentId;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getStudentIds() {
        return studentIds;
    }

    public void setStudentIds(String[] studentIds) {
        this.studentIds = studentIds;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}
