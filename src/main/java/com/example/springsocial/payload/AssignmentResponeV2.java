package com.example.springsocial.payload;

import com.example.springsocial.model.AssignmentV2;

public class AssignmentResponeV2 {
    private AssignmentV2 assignment;
    private AssignmentV2[] assignments;

    private boolean success;
    private int code;
    private String message;

    public AssignmentResponeV2(int code, boolean success,String message ,AssignmentV2 assignment) {
        this.code = code;
        this.success = success;
        this.message=message;
        this.assignment = assignment;
    }
    public AssignmentResponeV2(int code, boolean success, AssignmentV2[] assignments) {
        this.code = code;
        this.success = success;
        this.assignments = assignments;
    }

    public AssignmentV2 getAssignment() {
        return assignment;
    }

    public void setAssignment(AssignmentV2 assignment) {
        this.assignment = assignment;
    }

    public AssignmentV2[] getAssignments() {
        return assignments;
    }

    public void setAssignments(AssignmentV2[] assignments) {
        this.assignments = assignments;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
