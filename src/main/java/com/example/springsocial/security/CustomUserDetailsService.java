package com.example.springsocial.security;


import com.example.springsocial.exception.ResourceNotFoundException;
import com.example.springsocial.model.User;
import com.example.springsocial.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;




@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email : " + email)
                );

        return UserPrincipal.create(user);
    }
    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "_id", id)
        );

        return UserPrincipal.create(user);
    }
    @Transactional
    public User loadUserByActivationCode(String code) {
        User user = userRepository.findByActivationCode(code).orElseThrow(
                () -> new ResourceNotFoundException("users", "activation_code", code)
        );

        return user;
    }
    @Transactional
    public User loadUserByEmail(String email) {

        User user=userRepository.findByEmail(email).orElse(null);
        return user;
    }
    @Transactional
    public User loadUserBy_id(String _id) {
        User user=userRepository.findBy_id(_id).orElseThrow(()
                -> new ResourceNotFoundException("users", "_id", _id));
        return user;
    }
}