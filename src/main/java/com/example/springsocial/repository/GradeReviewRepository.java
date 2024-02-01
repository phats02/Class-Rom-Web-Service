package com.example.springsocial.repository;

import com.example.springsocial.model.GradeReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeReviewRepository extends JpaRepository<GradeReview, Long> {
    GradeReview findBy_id(String _id);
    GradeReview findByAssignmentId(String assignmentId);
}
