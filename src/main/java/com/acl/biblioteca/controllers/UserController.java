package com.acl.biblioteca.controllers;

import com.acl.biblioteca.Response;
import com.acl.biblioteca.models.User;
import com.acl.biblioteca.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user) {
        Response response =  userService.registerUser(user);
        if (response.getStatus() == 201) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(response.getStatus()).body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        String email = user.getEmail();
        String password = user.getPassword();
        Response response = userService.authenticateUser(email, password);
        if (response.getStatus() == 200) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(response.getStatus()).body(response);
        }
    }

}
