package com.example.springsocial.model;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public class AssignmentV2<T> implements Cloneable {

    private String _id;

    private LocalDate createdAt;

    private LocalDate updatedAt;

    private String name;

    private float point;

    private T[] grades ;
    private String grade;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public LocalDate getCreated_at() {
        return createdAt;
    }

    public void setCreated_at(LocalDate created_at) {
        this.createdAt = created_at;
    }

    public LocalDate getUpdate_at() {
        return updatedAt;
    }

    public void setUpdate_at(LocalDate update_at) {
        this.updatedAt = update_at;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPoint() {
        return point;
    }

    public void setPoint(float point) {
        this.point = point;
    }

    public T[] getGrades() {
        return grades;
    }

    public void setGrades(T[] grades) {
        this.grades = grades;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public AssignmentV2<T> clone() {
        try {
            return (AssignmentV2<T>) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
