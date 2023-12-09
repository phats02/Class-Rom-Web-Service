package com.example.springsocial.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "classroom", uniqueConstraints = {
        @UniqueConstraint(columnNames = "class_id")
})
public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_id")
    private Long classId;
    @Column(name = "teacher_id")
    private Long teacherId;
    @Column(name = "class_code")
    private String classCode;
    @NotNull
    @Column(name = "class_name")
    private String className;
    private String description;

    @Column(name = "time_update")
    private LocalDate timeUpdate;
    @Column(name = "time_create")
    private LocalDate timeCreate;
    @Column(name = "is_finalgrade")
    private boolean isFinalGrade;



    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
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

    public LocalDate getTimeUpdate() {
        return timeUpdate;
    }

    public void setTimeUpdate(LocalDate timeUpdate) {
        this.timeUpdate = timeUpdate;
    }

    public LocalDate getTimeCreate() {
        return timeCreate;
    }

    public void setTimeCreate(LocalDate timeCreate) {
        this.timeCreate = timeCreate;
    }

    public boolean isFinalGrade() {
        return isFinalGrade;
    }

    public void setFinalGrade(boolean finalGrade) {
        isFinalGrade = finalGrade;
    }


}
