package com.example.springsocial.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;


@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;


    @Column(name="_id")
    private String _id;

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

    @Column(name="google_id")
    private String google_id;
    @Column(name="facebook_id")
    private String facebook_id;
    @Column(name="forgot_password_code")
    private String forgot_password_code;
    @Column(name="student")
    private String student;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;


    private String providerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return null;
        return List.of(new SimpleGrantedAuthority(this.status==0?"ROLE_ADMIN":"ROLE_USER"));
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
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


    public AuthProvider getProvider() {
        return provider;
    }

    public void setProvider(AuthProvider provider) {
        this.provider = provider;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public LocalDate getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(LocalDate update_at) {
        this.update_at = update_at;
    }

    public LocalDate getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDate created_at) {
        this.created_at = created_at;
    }

    public String getGoogle_id() {
        return google_id;
    }

    public void setGoogle_id(String google_id) {
        this.google_id = google_id;
    }

    public String getFacebook_id() {
        return facebook_id;
    }

    public void setFacebook_id(String facebook_id) {
        this.facebook_id = facebook_id;
    }

    public String getForgot_password_code() {
        return forgot_password_code;
    }

    public void setForgot_password_code(String forgot_password_code) {
        this.forgot_password_code = forgot_password_code;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }
}
