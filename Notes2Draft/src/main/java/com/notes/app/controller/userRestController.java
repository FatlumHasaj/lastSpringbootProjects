package com.notes.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.notes.app.entity.user;
import com.notes.app.service.userservice;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/users")
public class userRestController {

    @Autowired
    private userservice userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password, HttpSession session) {
        user foundUser = userService.login(email, password);
        if (foundUser != null) {
            // Store user ID in session upon successful login
            session.setAttribute("userId", foundUser.getId());
            return ResponseEntity.ok(foundUser);
        } else {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("{\"error\": \"Invalid email or password\"}");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestParam String username,
                                      @RequestParam String email,
                                      @RequestParam String password,
                                      HttpSession session) {
        user newUser = new user();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(password);
        
        user registeredUser = userService.register(newUser);

        if (registeredUser != null) {
            // Store user ID in session upon successful registration
            session.setAttribute("userId", registeredUser.getId());
            return ResponseEntity.ok(registeredUser);
        } else {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT) // 409 Conflict is good for "already exists"
                    .body("{\"error\": \"Email already in use\"}");
        }
    }
}