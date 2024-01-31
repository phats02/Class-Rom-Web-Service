package com.example.springsocial.repository;

import com.example.springsocial.model.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.springsocial.model.Grade;


@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
    Grade findBy_id(String _id);
}
