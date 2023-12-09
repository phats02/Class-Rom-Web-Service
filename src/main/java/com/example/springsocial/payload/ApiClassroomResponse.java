package com.example.springsocial.payload;
import java.time.LocalDate;

public class ApiClassroomResponse {

    private boolean success;
    private String classCode;
    private String className;
    private String description;
    private Long id;
    private Long teacherId;
    private LocalDate timeCreate;


    private LocalDate timeUpdate;
    private boolean isFinalGrade;

    public ApiClassroomResponse(boolean success,String classCode,String className,String description,Long id,Long teacherId,LocalDate timeCreate,LocalDate timeUpdate,boolean isFinalGrade){
        this.success=success;
        this.classCode=classCode;
        this.className=className;
        this.description=description;
        this.id=id;
        this.teacherId=teacherId;
        this.timeCreate=timeCreate;
        this.timeUpdate=timeUpdate;
        this.isFinalGrade=isFinalGrade;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public LocalDate getTimeCreate() {
        return timeCreate;
    }

    public void setTimeCreate(LocalDate timeCreate) {
        this.timeCreate = timeCreate;
    }

    public LocalDate getTimeUpdate() {
        return timeUpdate;
    }

    public void setTimeUpdate(LocalDate timeUpdate) {
        this.timeUpdate = timeUpdate;
    }

    public boolean isFinalGrade() {
        return isFinalGrade;
    }

    public void setFinalGrade(boolean finalGrade) {
        isFinalGrade = finalGrade;
    }



}
