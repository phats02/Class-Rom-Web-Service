package com.example.springsocial.repository;

import com.example.springsocial.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment findBy_id(String _id);
    Comment findByUserId(String userId);
}
