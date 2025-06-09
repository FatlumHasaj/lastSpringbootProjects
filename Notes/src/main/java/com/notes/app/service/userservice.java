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

//    public user register(user newUser) {
//        if (userRepo.findByEmail(newUser.getEmail()) != null) {
//            return null; // Email already exists
//        }
//        return userRepo.save(newUser);
//    }
}
