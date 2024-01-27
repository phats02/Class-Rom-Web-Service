package com.example.springsocial.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;


public class ClassroomRespone {

    private Long id;
    private String _id;
    private String[] teachers;
    private String[] students;
    private String name;
    private String description;
    private LocalDate created_at;
    private LocalDate update_at;
    private String slug;
    private String owner;
    private String join_id;

    private String[] assignments;
    private String[] studentIds;

    private User[] teacher_arr;
    private User[] student_arr;

    public User[] getTeacher_arr() {
        return teacher_arr;
    }

    public void setTeacher_arr(User[] teacher_arr) {
        this.teacher_arr = teacher_arr;
    }

    public User[] getStudent_arr() {
        return student_arr;
    }

    public void setStudent_arr(User[] student_arr) {
        this.student_arr = student_arr;
    }

    public String[] getStudentsIds() {
        return studentIds;
    }

    public void setStudentsIds(String[] studentIds) {
        this.studentIds = studentIds;
    }

    public String[] getTeachers() {
        return teachers;
    }

    public void setTeachers(String[] teachers,String type) {
        if(Objects.equals(type, "create")){
            this.teachers = teachers;
        }
        else if(Objects.equals(type,"update")){
            this.teachers = teachers;
        }
    }

    public String[] getStudents() {
        return students;
    }

    public void setStudents(String[] students,String type   ) {

        this.students = students;

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
        return this.created_at;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.created_at = createdAt;
    }

    public LocalDate getUpdateAt() {
        return update_at;
    }

    public void setUpdateAt(LocalDate updateAt) {
        this.update_at = updateAt;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getJoinId() {
        return join_id;
    }

    public void setJoinId(String joinId) {
        this.join_id = joinId;
    }

    public String[] getAssignments() {
        return assignments;
    }

    public void setAssignments(String[] assignments) {
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
