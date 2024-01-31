package com.example.springsocial.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "assignments", uniqueConstraints = {
        @UniqueConstraint(columnNames = "_id")
})
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "_id")
    private String _id;
    @Column(name = "created_at")
    private LocalDate created_at;
    @Column(name = "updated_at")
    private LocalDate update_at;
    @Column(name="name")
    private String name;
    @Column(name="point")
    private float point;

    @Column(name="grades")
    private String grades ;

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

    public LocalDate getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(LocalDate created_at) {
        this.created_at = created_at;
    }

    public LocalDate getUpdateAt() {
        return update_at;
    }

    public void setUpdateAt(LocalDate update_at) {
        this.update_at = update_at;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPoint() {
        return point;
    }

    public void setPoint(float point) {
        this.point = point;
    }

    public String getGrades() {
        return grades;
    }

    public void setGrades(String grades) {
        this.grades = grades;
    }
}
