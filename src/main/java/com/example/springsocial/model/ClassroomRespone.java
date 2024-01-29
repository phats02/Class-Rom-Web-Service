package com.example.springsocial.model;

import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

@Repository
public class ClassroomRespone<T> {

    private Long id;
    private String _id;
    private T[] teachers;
    private T[] students;
    private String name;
    private String description;
    private LocalDate created_at;
    private LocalDate update_at;
    private String slug;
    private T[] owner;
    private String join_id;

    private String[] assignments;
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

    public T[] getOwner() {
        return owner;
    }

    public void setOwner(T[] owner) {
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




//package com.example.springsocial.model;
//
//import org.springframework.stereotype.Repository;
//
//import javax.persistence.*;
//import javax.validation.constraints.NotNull;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.Objects;
//
//@Repository
//public class ClassroomRespone<T> {
//
//    private Long id;
//    private String _id;
//    private ArrayList<T> teachers;
//    private ArrayList<T> students;
//    private String name;
//    private String description;
//    private LocalDate created_at;
//    private LocalDate update_at;
//    private String slug;
//    private ArrayList<T>owner;
//    private String join_id;
//
//    private ArrayList<T> assignments;
//    private ArrayList<T>studentIds;
//
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String get_id() {
//        return _id;
//    }
//
//    public void set_id(String _id) {
//        this._id = _id;
//    }
//
//    public ArrayList<T> getTeachers() {
//        return teachers;
//    }
//
//    public void setTeachers(ArrayList<T> teachers) {
//        this.teachers = teachers;
//    }
//
//    public ArrayList<T> getStudents() {
//        return students;
//    }
//
//    public void setStudents(ArrayList<T> students) {
//        this.students = students;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public LocalDate getCreatedAt() {
//        return created_at;
//    }
//
//    public void setCreatedAt(LocalDate created_at) {
//        this.created_at = created_at;
//    }
//
//    public LocalDate getUpdateAt() {
//        return update_at;
//    }
//
//    public void setUpdateAt(LocalDate update_at) {
//        this.update_at = update_at;
//    }
//
//    public String getSlug() {
//        return slug;
//    }
//
//    public void setSlug(String slug) {
//        this.slug = slug;
//    }
//
//    public ArrayList<T> getOwner() {
//        return owner;
//    }
//
//    public void setOwner(ArrayList<T> owner) {
//        this.owner = owner;
//    }
//
//    public String getJoinId() {
//        return join_id;
//    }
//
//    public void setJoinId(String join_id) {
//        this.join_id = join_id;
//    }
//
//    public ArrayList<T> getAssignments() {
//        return assignments;
//    }
//
//    public void setAssignments(ArrayList<T> assignments) {
//        this.assignments = assignments;
//    }
//
//    public ArrayList<T> getStudentIds() {
//        return studentIds;
//    }
//
//    public void setStudentIds(ArrayList<T> studentIds) {
//        this.studentIds = studentIds;
//    }
//}
//
