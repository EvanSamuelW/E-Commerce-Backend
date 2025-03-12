package com.example.demo.controller;



import com.example.demo.model.LoginRequest;
import com.example.demo.model.LoginResponse;
import com.example.demo.model.User;
import com.example.demo.service.JWTService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    private UserService service;

    @Autowired
    private JWTService jwtService;


    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return service.register(user);

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login( @RequestBody LoginRequest loginRequest) {
        User authenticatedUser = service.authenticate(loginRequest.getEmail(), loginRequest.getPassword());

        // If authentication is successful, generate JWT token
        if (authenticatedUser != null) {
            // Generate JWT token with username and user_id
            String token = jwtService.generateToken(authenticatedUser.getUsername(), authenticatedUser.getId());
            return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION,"Bearer "+ token).body(new LoginResponse(token));
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }
}
