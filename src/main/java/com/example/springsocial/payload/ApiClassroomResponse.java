package com.example.springsocial.payload;
import com.example.springsocial.model.Classroom;

import java.time.LocalDate;

public class ApiClassroomResponse {

    private boolean success;
    private int code;
    private Classroom courses;

    public ApiClassroomResponse(boolean success,int code,Classroom courses){
        this.success=success;
        this.code=code;
        this.courses=courses;
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

    public Classroom getCourses() {
        return courses;
    }

    public void setCourses(Classroom courses) {
        this.courses = courses;
    }








}
