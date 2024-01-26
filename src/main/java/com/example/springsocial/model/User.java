package com.example.springsocial.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.time.LocalDate;


@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Email
    @Column(nullable = false)
    private String email;

    @JsonIgnore
    private String password;

    @Column(name = "activation_code")
    private String activationCode;

    @Column(name = "update_at")
    private LocalDate update_at;

    @Column(name = "created_at")
    private LocalDate created_at;

    @Column(name = "status")
    private int status=0;

    @Column(name="type")
    private int type=1;
    @Column(name="phone_number")
    private int phone_number;
    @Column(name="google_id")
    private String google_id;
    @Column(name="facebook_id")
    private String facebook_id;
    @Column(name="forgot_password_code")
    private String forgot_password_code;




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public LocalDate getUpdateAt() {
        return update_at;
    }

    public void setUpdateAt(LocalDate updateAt) {
        this.update_at = updateAt;
    }

    public LocalDate getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.created_at = createdAt;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPhoneNumber() {
        return phone_number;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phone_number = phoneNumber;
    }

    public String getGoogleId() {
        return google_id;
    }

    public void setGoogleId(String googleId) {
        this.google_id = googleId;
    }

    public String getFacebookId() {
        return facebook_id;
    }

    public void setFacebookId(String facebookId) {
        this.facebook_id = facebookId;
    }

    public String getForgotPasswordCode() {
        return forgot_password_code;
    }

    public void setForgotPasswordCode(String forgotPasswordCode) {
        this.forgot_password_code = forgotPasswordCode;
    }







}
