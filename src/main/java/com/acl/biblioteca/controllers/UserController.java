package com.acl.biblioteca.controllers;

import com.acl.biblioteca.response.Response;
import com.acl.biblioteca.models.User;
import com.acl.biblioteca.response.ResponseUser;
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
        ResponseUser responseUser =  userService.registerUser(user);
        if (responseUser.getStatus() == 201) {
            return ResponseEntity.ok(responseUser);
        } else {
            return ResponseEntity.status(responseUser.getStatus()).body(responseUser);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        String email = user.getEmail();
        String password = user.getPassword();
        ResponseUser responseUser = userService.authenticateUser(email, password);
        if (responseUser.getStatus() == 200) {
            return ResponseEntity.ok(responseUser);
        } else {
            return ResponseEntity.status(responseUser.getStatus()).body(responseUser);
        }
    }

}
