package com.example.springsocial.payload;

import javax.validation.constraints.NotBlank;

public class ClassroomRequest {
    @NotBlank
    private String className;
    private String description;

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
}
