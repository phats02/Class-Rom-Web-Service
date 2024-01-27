package com.example.springsocial.security;

import com.example.springsocial.exception.ResourceNotFoundException;
import com.example.springsocial.model.Classroom;
import com.example.springsocial.model.User;
import com.example.springsocial.repository.ClassroomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CustomCourseDetailsService implements UserDetailsService {

    @Autowired
    ClassroomRepository classroomRepository;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }

    @Transactional
    public Classroom loadCoursesById(String id) {
        Classroom classroom = classroomRepository.findBy_id(id).orElseThrow(
                () -> new ResourceNotFoundException("courses", "_id", id)
        );

        return classroom;
    }
}
