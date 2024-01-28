package com.example.springsocial.controller;

import com.example.springsocial.security.CustomUserDetailsService;
import com.example.springsocial.util.RandomStringSingleton;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder passwordEncoder, TokenProvider tokenProvider, JavaMailSender mailSender, CustomUserDetailsService customUserDetailsService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.mailSender = mailSender;
        this.customUserDetailsService = customUserDetailsService;
    }
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        User user = customUserDetailsService.loadUserByEmail(loginRequest.getEmail());

        if (user != null) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = tokenProvider.createToken(authentication);
            AuthResponse authResponse = new AuthResponse(200, true, user, token);

//            return ResponseEntity.ok(authResponse).header();
            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,token).body(authResponse);
        } else {

            AuthResponse authResponse = new AuthResponse(200, false, user, null);
            return ResponseEntity.ok(authResponse);
        }
    }


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest, HttpServletRequest request)
            throws MessagingException, UnsupportedEncodingException {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new BadRequestException("Email address already in use.");
        }
        LocalDate currentTime = LocalDate.now();
        // Creating user's account
        User user = new User();
        //design pattern  singleton
        String IDrandomCode = RandomStringSingleton.getInstance(24).make();
        user.set_id(IDrandomCode);
        user.setCreatedAt(currentTime);
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(signUpRequest.getPassword());
        user.setProvider(AuthProvider.local);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //Generate random verification code
        String randomCode = RandomString.make(64);
        user.setActivationCode(randomCode);


        //sent verification mail
        String siteURL = UrlUtils.getSiteURL(request);
        sendVerificationEmail(user, siteURL);

        User result = userRepository.save(user);


        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/me")
                .buildAndExpand(result.get_id()).toUri();

        ApiResponse response = new ApiResponse(200, true, "Successful account registration. An email has been sent. Please check your inbox.");

        return ResponseEntity.created(location).body(response);


    }

    public void sendVerificationEmail(User user, String siteURL)
            throws MessagingException, UnsupportedEncodingException {


        final String username = "ggclassroom2324@gmail.com";
        final String password = "bbscdrwfniqfdkpc";
        String mailContent = "Dear " + user.getName() + "\n\n";
        String verifyURL = siteURL + "/auth/activation?code=" + user.getActivationCode();

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


        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }


    public boolean verify(String verification_code) {
        User user = customUserDetailsService.loadUserByActivationCode(verification_code);
        if (user == null) {
            return false;
        } else {
            user.setStatus(1);
            userRepository.save(user);
            return true;
        }
    }

    @GetMapping("/activation")
    public ResponseEntity<ApiResponse> verifyAccount(@Param("code") String code) {
        boolean verified = verify(code);
        User user = customUserDetailsService.loadUserByActivationCode(code);
        String message = "";
        ApiResponse response = null;
        if (verified) {
            message = "Account activation successful.";
            user.setActivationCode("");
            userRepository.save(user);
            response = new ApiResponse(200, verified, message);

        } else {
            message = "Your account has been disabled. Please contact the administrator.";
            response = new ApiResponse(400, verified, message);
        }


        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/me")
                .buildAndExpand() // You need to specify the path variables here if needed
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/google/token")
    public ResponseEntity<?> socialLogin(@RequestParam("code") String code) {
        System.out.println("code: " + code);
        return ResponseEntity.ok(code);
    }
    @GetMapping("/health")
    public String getHealth() {
        System.out.println(System.getenv("DDEV_CLIENT_HOST"));
        System.out.println(System.getenv("DPRODUCTION_CLIENT_HOST"));
        System.out.println(System.getenv("DFRONT_END_SITE"));
        return "worked!";
    }

}
