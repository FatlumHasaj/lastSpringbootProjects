package com.notes.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.notes.app.entity.user;
import com.notes.app.repository.usersrepo;

@Service
public class userservice {

    @Autowired
    private usersrepo userRepo;

    public user login(String email, String password) {
        return userRepo.findByEmailAndPassword(email, password);
    }

    public user register(user newUser) {
        // Check if a user with the given email already exists
        if (userRepo.findByEmail(newUser.getEmail()) != null) {
            // Returning null indicates failure (email exists)
            return null; 
        }
        // If email is unique, save the new user
        return userRepo.save(newUser);
    }
}