package com.example.springsocial.payload;

import com.example.springsocial.model.ClassroomV2;

import java.time.LocalDate;

public class ApiClassroomResponse<T> {

    // @Autowired
    private boolean success;
    // @Autowired
    private int code;
    // @Autowired
    private ClassroomV2 course = new ClassroomV2();
    private ClassroomV2[] courses;
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

    public ClassroomV2[] getCourses() {
        return courses;
    }

    public void setCourses(ClassroomV2[] courses) {
        this.courses = courses;
    }

    public ApiClassroomResponse(boolean success, int code, ClassroomV2 classroom) {

        this.success = success;
        this.code = code;
        this.course=classroom;

    }
    public ApiClassroomResponse(boolean success, int code, ClassroomV2[] classroom) {

        this.success = success;
        this.code = code;
        this.courses=classroom;

    }

    public ClassroomV2 getCourse() {
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


    public void setCourse(ClassroomV2 course) {
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
