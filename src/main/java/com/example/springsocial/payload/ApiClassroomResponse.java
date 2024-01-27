package com.example.springsocial.payload;
import com.example.springsocial.model.Classroom;
import com.example.springsocial.model.ClassroomRespone;
import com.example.springsocial.model.User;
import com.example.springsocial.security.CustomUserDetailsService;
import com.example.springsocial.util.ConvertStringToArrayList;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Objects;

public class ApiClassroomResponse {

    private boolean success;
    private int code;
    private ClassroomRespone course=new ClassroomRespone();
    private String[] teachers;
    private String[] students;
    private String description;
    private String name;


    private String owner;
    private String joinId;
    private String[] assignments;
    private String[] studentsIds;
    private String _id;
    private LocalDate created_at;
    private LocalDate update_at;
    private String slug;

    @Autowired
    private ConvertStringToArrayList convertStringToArrayList;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;


public ApiClassroomResponse(boolean success, int code, Classroom classroom,String type) {
    if(Objects.equals(type, "create")){
        this.course.setAssignments(classroom.getAssignments() != null
                ? convertStringToArrayList.convertToArrayList(classroom.getAssignments()).toArray(new String[0])
                : new String[0]);
        this.course.setStudents(classroom.getStudents() != null
                ? convertStringToArrayList.convertToArrayList(classroom.getStudents()).toArray(new String[0])
                : new String[0],type);
        this.course.setStudentsIds(classroom.getStudentsIds() != null
                ? convertStringToArrayList.convertToArrayList(classroom.getStudentsIds()).toArray(new String[0])
                : new String[0]);
        this.course.setTeachers(classroom.getTeachers() != null
                ? convertStringToArrayList.convertToArrayList(classroom.getTeachers()).toArray(new String[0])
                : new String[0],type);

    }
    else if(Objects.equals(type,type)){

        this.course.setTeachers(teachers,type);
        this.course.setStudents(students,type);



    }
    this.success = success;
    this.code = code;
    this.course.setOwner(classroom.getOwner());
    this.course.set_id(classroom.get_id());
    this.course.setName(classroom.getName());
    this.course.setDescription(classroom.getDescription());
    this.course.setSlug(classroom.getSlug());
    this.course.setJoinId(classroom.getJoinId());
    this.course.setCreatedAt(classroom.getCreatedAt());
    this.course.setUpdateAt(classroom.getUpdateAt());

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

    public ClassroomRespone getCourse() {
        return course;
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
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
