package com.example.springsocial.controller;

import com.example.springsocial.model.Classroom;
import com.example.springsocial.model.ClassroomRespone;
import com.example.springsocial.security.CustomCourseDetailsService;
import com.example.springsocial.util.ConvertStringToArrayList;
import com.example.springsocial.util.StringToTextArrayPosgre;
import com.example.springsocial.model.User;
import com.example.springsocial.payload.ApiResponse;
import com.example.springsocial.payload.AuthResponse;
import com.example.springsocial.payload.ClassroomRequest;
import com.example.springsocial.payload.LoginRequest;
import com.example.springsocial.payload.ApiClassroomResponse;
import com.example.springsocial.repository.ClassroomRepository;
import com.example.springsocial.repository.UserRepository;
import com.example.springsocial.security.CustomUserDetailsService;
import com.example.springsocial.util.StringToTextArrayPosgre;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.example.springsocial.repository.UserRepository;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;


@RestController
@RequestMapping("/courses")
public class ClassroomController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private ClassroomRepository classroomRepository;
    @Autowired
    private StringToTextArrayPosgre stringToTextArrayPosgre;
    @Autowired
    private CustomCourseDetailsService customCourseDetailsService;
    @Autowired
    private ConvertStringToArrayList convertStringToArrayList;
    @Autowired
    private ClassroomRespone course = new ClassroomRespone();


    private static void printVariableType(Object variable) {
        // Get the runtime type of the variable
        Class<?> variableType = variable.getClass();

        // Print the variable type
        System.out.println("Variable type: " + variableType.getName());
    }

    @PostMapping("/store")
    public ResponseEntity<?> createClassroom(@Valid @RequestBody ClassroomRequest classroomRequest) {

        String randomCodeJoinId = RandomString.make(8);
        String randomCodeIdClass = RandomString.make(24);
        LocalDate currentTime = LocalDate.now();
        ArrayList<String> teachers_id = new ArrayList<String>(classroomRequest.getTeachers().length);
        ArrayList<String> students_id = new ArrayList<String>(classroomRequest.getStudents().length);

        Classroom classroom = new Classroom();
        classroom.set_id(randomCodeIdClass);
        classroom.setName(classroomRequest.getName());
        classroom.setDescription(classroomRequest.getDescription());
        classroom.setJoinId(randomCodeJoinId);
        classroom.setCreatedAt(currentTime);
        classroom.setUpdateAt(currentTime);
        classroom.setSlug(classroomRequest.getName().toLowerCase());
        for (int i = 0; i < classroomRequest.getTeachers().length; i++) {
            teachers_id.add(classroomRequest.getTeachers()[i]);
        }
        for (int i = 0; i < classroomRequest.getStudents().length; i++) {
            students_id.add(classroomRequest.getStudents()[i]);
        }
        String[] teachersToString = teachers_id.toString().substring(1, teachers_id.toString().length() - 1).split(", ");
        String[] studentsToString = students_id.toString().substring(1, students_id.toString().length() - 1).split(", ");


        classroom.setTeachers(stringToTextArrayPosgre.convert(teachersToString));
        classroom.setStudents(stringToTextArrayPosgre.convert(studentsToString));
        classroom.setOwner(classroomRequest.getOwner());


        Classroom result =classroomRepository.save(classroom);

        String[] strStutdents = classroom.getStudents() != null
                ? convertStringToArrayList.convertToArrayList(classroom.getStudents()).toArray(new String[0])
                : new String[0];
        String[] strTeachers = classroom.getTeachers() != null
                ? convertStringToArrayList.convertToArrayList(classroom.getTeachers()).toArray(new String[0])
                : new String[0];
        String[] strAssignments = classroom.getAssignments() != null
                ? convertStringToArrayList.convertToArrayList(classroom.getAssignments()).toArray(new String[0])
                : new String[0];
        String[] strStudentsIds = classroom.getStudentsIds() != null
                ? convertStringToArrayList.convertToArrayList(classroom.getStudentsIds()).toArray(new String[0])
                : new String[0];

        this.course.setAssignments(strAssignments);
        this.course.setStudents(strStutdents);
        this.course.setStudentIds(strStudentsIds);
        this.course.setTeachers(strTeachers);
        this.course.setOwner(strTeachers);
        this.course.set_id(classroom.get_id());
        this.course.setName(classroom.getName());
        this.course.setDescription(classroom.getDescription());
        this.course.setSlug(classroom.getSlug());
        this.course.setJoinId(classroom.getJoinId());
        this.course.setCreatedAt(classroom.getCreatedAt());
        this.course.setUpdateAt(classroom.getUpdateAt());

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .buildAndExpand(result.get_id()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiClassroomResponse(true, 200, course));

    }

    @GetMapping(params = "slug")
    public ResponseEntity<ApiClassroomResponse> showOneClassroom(@RequestParam(value = "slug", defaultValue = "") String slug) {
        Classroom classroom = classroomRepository.findBySlug(slug);
        if (classroom == null) {
            return ResponseEntity.ok(new ApiClassroomResponse(false, 200, null));
        } else {
            String[] strStutdents = classroom.getStudents() != null
                    ? convertStringToArrayList.convertToArrayList(classroom.getStudents()).toArray(new String[0])
                    : new String[0];
            String[] strTeachers = classroom.getTeachers() != null
                    ? convertStringToArrayList.convertToArrayList(classroom.getTeachers()).toArray(new String[0])
                    : new String[0];
            String[] strAssignments = classroom.getAssignments() != null
                    ? convertStringToArrayList.convertToArrayList(classroom.getAssignments()).toArray(new String[0])
                    : new String[0];
            String[] strStudentsIds = classroom.getStudentsIds() != null
                    ? convertStringToArrayList.convertToArrayList(classroom.getStudentsIds()).toArray(new String[0])
                    : new String[0];
            User[] userTeachers = new User[strTeachers.length];
            User[] userStudents = new User[strStutdents.length];
            User[] userStudentsIds = new User[strStudentsIds.length];
            for (int i = 0; i < strTeachers.length; i++) {
                userTeachers[i] = customUserDetailsService.loadUserBy_id(strTeachers[i]);
            }
            for (int i = 0; i < strStutdents.length; i++) {
                userStudents[i] = customUserDetailsService.loadUserBy_id(strStutdents[i]);
            }
            for (int i = 0; i < strStudentsIds.length; i++) {
                userStudentsIds[i] = customUserDetailsService.loadUserBy_id(strStudentsIds[i]);
            }
            this.course.setTeachers(userTeachers);
            this.course.setStudents(userStudents);
            this.course.setStudentIds(userStudentsIds);
            this.course.setOwner(userTeachers);
            this.course.set_id(classroom.get_id());
            this.course.setName(classroom.getName());
            this.course.setDescription(classroom.getDescription());
            this.course.setSlug(classroom.getSlug());
            this.course.setJoinId(classroom.getJoinId());
            this.course.setCreatedAt(classroom.getCreatedAt());
            this.course.setUpdateAt(classroom.getUpdateAt());
            this.course.setAssignments(strAssignments);


            URI location = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .buildAndExpand(classroom.get_id()).toUri();

            return ResponseEntity.created(location)
                    .body(new ApiClassroomResponse(true, 200, course));
        }
    }

    @GetMapping("/test")
    public String getTest() {

        return "worked!";
    }
}
