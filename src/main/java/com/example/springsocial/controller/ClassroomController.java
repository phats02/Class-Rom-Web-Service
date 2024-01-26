package com.example.springsocial.controller;

import com.example.springsocial.model.Classroom;
import com.example.springsocial.model.User;
import com.example.springsocial.payload.ApiResponse;
import com.example.springsocial.payload.AuthResponse;
import com.example.springsocial.payload.ClassroomRequest;
import com.example.springsocial.payload.LoginRequest;
import com.example.springsocial.payload.ApiClassroomResponse;
import com.example.springsocial.repository.ClassroomRepository;
import com.example.springsocial.repository.UserRepository;
import com.example.springsocial.security.CustomUserDetailsService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
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


@RestController
@RequestMapping("/courses")
public class ClassroomController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private ClassroomRepository classroomRepository;
    private static void printVariableType(Object variable) {
        // Get the runtime type of the variable
        Class<?> variableType = variable.getClass();

        // Print the variable type
        System.out.println("Variable type: " + variableType.getName());
    }
    @PostMapping("/store")
    public ResponseEntity<?> createClassroom(@Valid @RequestBody ClassroomRequest classroomRequest) {

        String randomCode = RandomString.make(8);
        LocalDate currentTime = LocalDate.now();
        String[] teachers_id= new String[classroomRequest.getTeachers().length];
        String[] students_id= new String[classroomRequest.getStudents().length];
        Classroom classroom = new Classroom();


        classroom.setName(classroomRequest.getName());
        classroom.setDescription(classroomRequest.getDescription());
        classroom.setJoinId(randomCode);
        classroom.setCreatedAt(currentTime);
        classroom.setUpdateAt(currentTime);
        for(int i = 0; i < classroomRequest.getTeachers().length; i++) {
            classroom.setTeachers(classroomRequest.getTeachers()[i]);
            printVariableType(teachers_id[i]);
        }
        for(int i = 0; i < classroomRequest.getStudents().length; i++) {
            students_id[i] =(String)  classroomRequest.getStudents()[i];
            printVariableType(students_id[i]);
        }


        classroom.setTeachers(classroomRequest.getTeachers());
        classroom.setStudents(classroomRequest.getStudents());
        classroom.setOwner(classroomRequest.getOwner());

        Classroom result = classroomRepository.save(classroom);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .buildAndExpand(result.get_id()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiClassroomResponse(true, 200,result));

    }

    @GetMapping("/test")
    public String getTest() {

        return "worked!";
    }
}
