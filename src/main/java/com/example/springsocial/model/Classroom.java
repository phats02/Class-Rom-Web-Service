package com.example.springsocial.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;


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
}
