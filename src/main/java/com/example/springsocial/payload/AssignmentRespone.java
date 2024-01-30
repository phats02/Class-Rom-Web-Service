package com.example.springsocial.payload;

import com.example.springsocial.model.Assignment;
import com.example.springsocial.model.AssignmentV2;

public class AssignmentRespone {

    private Assignment assignment;
    private Assignment[] assignments;
    private AssignmentV2 assignmentV2;
    private AssignmentV2[] assignmentsResponeV2;

    private boolean success;
    // @Autowired
    private int code;

    public AssignmentRespone(int code,boolean success,Assignment assignment){
        this.code=code;
        this.success=success;
        this.assignment=assignment;


    }
    public AssignmentRespone(int code,boolean success,Assignment[] assignments){
        this.code=code;
        this.success=success;
        this.assignments=assignments;

    }
    public AssignmentRespone(int code, boolean success, AssignmentV2 assignmentV2){
        this.code=code;
        this.success=success;
        this.assignmentV2 = assignmentV2;

    }
    public AssignmentRespone(int code, boolean success, AssignmentV2[] assignmentsResponeV2){
        this.code=code;
        this.success=success;
        this.assignmentsResponeV2=assignmentsResponeV2;
    }

    public Assignment[] getAssignments() {
        return assignments;
    }

    public void setAssignments(Assignment[] assignments) {
        this.assignments = assignments;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
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

    public AssignmentV2 getAssignmentResponeV2() {
        return assignmentV2;
    }

    public void setAssignmentResponeV2(AssignmentV2 assignmentV2) {
        this.assignmentV2 = assignmentV2;
    }

    public AssignmentV2[] getAssignmentsResponeV2() {
        return assignmentsResponeV2;
    }

    public void setAssignmentsResponeV2(AssignmentV2[] assignmentsResponeV2) {
        this.assignmentsResponeV2 = assignmentsResponeV2;
    }
}
