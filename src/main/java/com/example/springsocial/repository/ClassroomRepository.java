package com.example.springsocial.repository;

import com.example.springsocial.model.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
    Optional<Classroom> findBy_id(String _id);
    Classroom findBySlug(String slug);
    Classroom[] findByOwner(String owner);
    Classroom[] findByStudents(String students);
    Classroom[] findByTeachers(String teachers);
    List<Classroom> findAll();
}
