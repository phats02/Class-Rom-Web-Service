package com.example.springsocial.repository;

import com.example.springsocial.model.Assignment;
import com.example.springsocial.model.AssignmentV2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignmentV2Repository extends JpaRepository<Assignment, Long> {
    AssignmentV2 findBy_id(String _id);
}
