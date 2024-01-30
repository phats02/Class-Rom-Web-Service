package com.example.springsocial.payload;

import com.example.springsocial.model.Classroom;
import com.example.springsocial.model.ClassroomRespone;
import com.example.springsocial.model.User;

import com.example.springsocial.repository.UserRepository;
import com.example.springsocial.security.CustomUserDetailsService;
import com.example.springsocial.util.ConvertStringToArrayList;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;
public class ApiClassroomResponse<T> {

    // @Autowired
    private boolean success;
    // @Autowired
    private int code;
    // @Autowired
    private ClassroomRespone course = new ClassroomRespone();
    private ClassroomRespone[] courses;
    private String[] teachers;
    // @Autowired
    private String[] students;
    // @Autowired
    private String description;
    // @Autowired
    private String name;
    // @Autowired
    private T owner;
    // @Autowired
    private String joinId;
    // @Autowired
    private String[] assignments;
    // @Autowired
    private String[] studentsIds;
    // @Autowired
    private String _id;
    // @Autowired
    private LocalDate created_at;
    // @Autowired
    private LocalDate update_at;
    // @Autowired
    private String slug;

    public ClassroomRespone[] getCourses() {
        return courses;
    }

    public void setCourses(ClassroomRespone[] courses) {
        this.courses = courses;
    }

    public ApiClassroomResponse(boolean success, int code, ClassroomRespone classroom) {

        this.success = success;
        this.code = code;
        this.course=classroom;

    }
    public ApiClassroomResponse(boolean success, int code, ClassroomRespone[] classroom) {

        this.success = success;
        this.code = code;
        this.courses=classroom;

    }

    public ClassroomRespone getCourse() {
        return course;
    }

    public String[] getTeachers() {
        return teachers;
    }

    public void setTeachers(String[] teachers) {
        this.teachers = teachers;
    }

    public String[] getStudents() {
        return students;
    }

    public void setStudents(String[] students) {
        this.students = students;
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


    public void setCourse(ClassroomRespone course) {
        this.course = course;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public T getOwner() {
        return owner;
    }

    public void setOwner(T owner) {
        this.owner = owner;
    }

    public String getJoinId() {
        return joinId;
    }

    public void setJoinId(String joinId) {
        this.joinId = joinId;
    }

    public String[] getAssignments() {
        return assignments;
    }

    public void setAssignments(String[] assignments) {
        this.assignments = assignments;
    }

    public String[] getStudentsIds() {
        return studentsIds;
    }

    public void setStudentsIds(String[] studentsIds) {
        this.studentsIds = studentsIds;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public LocalDate getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDate created_at) {
        this.created_at = created_at;
    }

    public LocalDate getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(LocalDate update_at) {
        this.update_at = update_at;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
