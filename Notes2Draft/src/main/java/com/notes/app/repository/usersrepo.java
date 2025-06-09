package com.notes.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.notes.app.entity.user;

@Repository
public interface usersrepo extends JpaRepository<user, Long> {
    user findByEmailAndPassword(String email, String password);
    user findByEmail(String email);
}