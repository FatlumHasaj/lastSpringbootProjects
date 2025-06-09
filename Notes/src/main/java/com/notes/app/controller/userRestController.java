package com.notes.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.notes.app.entity.user;
import com.notes.app.service.userservice;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class userRestController {

    @Autowired
    private userservice userService;

    
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
//        user foundUser = userService.login(email, password);
//        if (foundUser != null) {
//            return ResponseEntity.ok(foundUser); // returns user JSON
//        } else {
//            return ResponseEntity
//                    .status(401) // Unauthorized
//                    .body("{\"error\": \"Invalid email or password\"}");
//        }
//    }

    @PostMapping("/login")
    public user login(@RequestParam String email, @RequestParam String password) {
        return userService.login(email, password);
    }

//    @PostMapping("/register")
//    public user register(@RequestParam String username,
//                         @RequestParam String email,
//                         @RequestParam String password) {
//        user newUser = new user();
//        newUser.setUsername(username);
//        newUser.setEmail(email);
//        newUser.setPassword(password);
//        return userService.register(newUser);
//    }
}
