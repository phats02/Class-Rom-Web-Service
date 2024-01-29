package com.example.springsocial.repository;

import com.example.springsocial.model.Classroom;
import com.example.springsocial.model.Invitation;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    Optional<Invitation> findBy_id(String _id);

    Invitation findByCourseId(String courseId);

    Invitation findByInviteCode(String inviteCode);

}
