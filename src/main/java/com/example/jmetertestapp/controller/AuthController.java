package com.example.jmetertestapp.controller;

import com.example.jmetertestapp.dto.UserDTO;
import com.example.jmetertestapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");
        
        Map<String, Object> response = new HashMap<>();
        
        if (username == null || password == null) {
            response.put("success", false);
            response.put("message", "Username and password are required");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        
        Optional<UserDTO> user = userService.getUserByUsername(username);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            response.put("success", true);
            response.put("message", "Login successful");
            response.put("user", user.get());
            response.put("token", "mock-jwt-token-" + System.currentTimeMillis());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("success", false);
            response.put("message", "Invalid credentials");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }
    
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody UserDTO userDTO) {
        Map<String, Object> response = new HashMap<>();
        
        if (userService.existsByUsername(userDTO.getUsername())) {
            response.put("success", false);
            response.put("message", "Username already exists");
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
        
        if (userService.existsByEmail(userDTO.getEmail())) {
            response.put("success", false);
            response.put("message", "Email already exists");
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
        
        try {
            UserDTO createdUser = userService.createUser(userDTO);
            response.put("success", true);
            response.put("message", "User registered successfully");
            response.put("user", createdUser);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Registration failed: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout(@RequestHeader(value = "Authorization", required = false) String token) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Logout successful");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping("/validate")
    public ResponseEntity<Map<String, Object>> validateToken(@RequestHeader(value = "Authorization", required = false) String token) {
        Map<String, Object> response = new HashMap<>();
        
        if (token == null || !token.startsWith("Bearer ")) {
            response.put("valid", false);
            response.put("message", "Invalid token format");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        
        // Mock token validation
        String actualToken = token.substring(7);
        if (actualToken.startsWith("mock-jwt-token-")) {
            response.put("valid", true);
            response.put("message", "Token is valid");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("valid", false);
            response.put("message", "Invalid token");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }
    
    @GetMapping("/profile")
    public ResponseEntity<Map<String, Object>> getProfile(@RequestHeader(value = "Authorization", required = false) String token) {
        Map<String, Object> response = new HashMap<>();
        
        if (token == null || !token.startsWith("Bearer ")) {
            response.put("success", false);
            response.put("message", "Authorization token required");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        
        // Mock user profile
        Map<String, Object> profile = new HashMap<>();
        profile.put("id", 1L);
        profile.put("username", "testuser");
        profile.put("email", "test@example.com");
        profile.put("firstName", "Test");
        profile.put("lastName", "User");
        profile.put("role", "USER");
        
        response.put("success", true);
        response.put("profile", profile);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
