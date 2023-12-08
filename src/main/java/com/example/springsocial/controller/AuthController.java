package com.example.springsocial.controller;

import com.example.springsocial.security.CustomUserDetailsService;
import com.example.springsocial.util.UrlUtils;
import com.example.springsocial.exception.BadRequestException;
import com.example.springsocial.model.AuthProvider;
import com.example.springsocial.model.User;
import com.example.springsocial.payload.ApiResponse;
import com.example.springsocial.payload.AuthResponse;
import com.example.springsocial.payload.LoginRequest;
import com.example.springsocial.payload.SignUpRequest;
import com.example.springsocial.repository.UserRepository;
import com.example.springsocial.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.time.LocalDate;


import java.time.LocalTime;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import net.bytebuddy.utility.RandomString;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        System.out.println(authentication);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.createToken(authentication);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest, HttpServletRequest request)
            throws MessagingException, UnsupportedEncodingException {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new BadRequestException("Email address already in use.");
        }
        LocalDate currentTime = LocalDate.now();
        // Creating user's account
        User user = new User();

        user.setDob(signUpRequest.getDob());
        user.setCreateTime(currentTime);
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(signUpRequest.getPassword());
        user.setProvider(AuthProvider.local);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(signUpRequest.getRole());

        //Generate random verification code
        String randomCode = RandomString.make(64);
        user.setVerificationCode(randomCode);


        //sent verification mail
        String siteURL = UrlUtils.getSiteURL(request);
        sendVerificationEmail(user, siteURL);

        User result = userRepository.save(user);


        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/me")
                .buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "User registered successfully@"));
    }

    public void sendVerificationEmail(User user, String siteURL)
            throws MessagingException, UnsupportedEncodingException {


        final String username = "ggclassroom2324@gmail.com";
        final String password = "bbscdrwfniqfdkpc";
        String mailContent = "Dear " + user.getName() + "\n\n";
        String verifyURL = siteURL + "/auth/verify?code=" + user.getVerificationCode();

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.socketFactory.port", "587");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(user.getEmail())
            );
            message.setSubject("GG Classroom");

            mailContent += "Please click the link below to verify your registration:" + "\n\n";
            mailContent += "<a href=\"" + verifyURL + "\">VERIFY</a>" + "\n\n";
            mailContent += "<p> Thank you <br>GG Classroom</p>";

            message.setContent(mailContent, "text/html; charset=utf-8"); // Set content type to HTML

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }


    public boolean verify(String verificationCode) {
        User user = customUserDetailsService.loadUserByVerificationCode(verificationCode);
        if (user == null) {
            return false;
        } else {
            user.setEmailVerified(true);
            System.out.println(user.getRole());
            if(user.getRole().equals("Student")){
                user.setStudentID(user.getId());
            }
            userRepository.save(user);
            return true;
        }
    }

    @GetMapping("/verify")
    public ResponseEntity<ApiResponse> verifyAccount(@Param("code") String code) {
        boolean verified = verify(code);

        String message = verified ? "Email verified successfully" : "Email verification failed";

        ApiResponse response = new ApiResponse(verified, message);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/me")
                .buildAndExpand() // You need to specify the path variables here if needed
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/health")
    public String getHealth() {
        return "worked!";
    }

}
