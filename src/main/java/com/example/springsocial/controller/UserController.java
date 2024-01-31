package com.example.springsocial.controller;

import com.example.springsocial.exception.ResourceNotFoundException;
import com.example.springsocial.model.User;
import com.example.springsocial.payload.StudentRequest;
import com.example.springsocial.payload.StudentRespone;
import com.example.springsocial.repository.UserRepository;
import com.example.springsocial.security.CurrentUser;
import com.example.springsocial.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "_id", userPrincipal.getId()));
    }
    @PutMapping("/users/{userId}")
    public ResponseEntity<?>update(@PathVariable String userId, @Valid @RequestBody StudentRequest studentRequest){
        User user=userRepository.findBy_id(userId).orElseThrow(()->new ResourceNotFoundException("User","_id",userId));
        user.setStudent(studentRequest.getStudent());
        userRepository.save(user);
        return ResponseEntity.ok(new StudentRespone(true,200,user));


    }
}
