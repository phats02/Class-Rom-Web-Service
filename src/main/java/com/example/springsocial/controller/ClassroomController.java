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
@RequestMapping("/classroom")
public class ClassroomController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private ClassroomRepository classroomRepository;

    @PostMapping("/create")
    public ResponseEntity<?> createClassroom(@Valid @RequestBody ClassroomRequest classroomRequest) {

        String randomCode = RandomString.make(5);
        LocalDate currentTime = LocalDate.now();


        Classroom classroom = new Classroom();
        classroom.setClassName(classroomRequest.getClassName());
        classroom.setDescription(classroomRequest.getDescription());
        classroom.setClassCode(randomCode);
        classroom.setTimeCreate(currentTime);
        classroom.setTimeUpdate(currentTime);
        classroom.setFinalGrade(false);
        classroom.setTeacherId(customUserDetailsService.loadUserByEmail(classroomRequest.getTeacherName()).getId());

        Classroom result = classroomRepository.save(classroom);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .buildAndExpand(result.getClassId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiClassroomResponse(true, result.getClassCode(), result.getClassName(), result.getDescription(),result.getClassId(),result.getTeacherId(),result.getTimeCreate(),result.getTimeUpdate(),result.isFinalGrade()));

    }

    @GetMapping("/test")
    public String getTest() {

        return "worked!";
    }
}
