package com.example.springsocial.model;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public class ClassroomV2<T> {

    private Long id;
    private String _id;
    private T[] teachers;
    private T[] students;
    private String name;
    private String description;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private String slug;
    private T owner;
    private String join_id;

    private T[] assignments;
    private T[] studentIds;


    public T[] getTeachers() {
        return teachers;
    }

    public void setTeachers(T[] teachers) {
        this.teachers = teachers;
    }

    public T[] getStudents() {
        return students;
    }

    public void setStudents(T[] students) {
        this.students = students;
    }

    public T[] getStudentIds() {
        return studentIds;
    }

    public void setStudentIds(T[] studentIds) {
        this.studentIds = studentIds;
    }


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

    public LocalDate getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdateAt() {
        return updatedAt;
    }

    public void setUpdateAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public T getOwner() {
        return owner;
    }

    public void setOwner(T owner) {
        this.owner = owner;
    }

    public String getJoinId() {
        return join_id;
    }

    public void setJoinId(String joinId) {
        this.join_id = joinId;
    }

    public T[] getAssignments() {
        return assignments;
    }

    public void setAssignments(T[] assignments) {
        this.assignments = assignments;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }




}





