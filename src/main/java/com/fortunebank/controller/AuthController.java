package com.fortunebank.controller;

import com.fortunebank.dto.AuthRequest;
import com.fortunebank.dto.AuthResponse;
import com.fortunebank.model.User;
import com.fortunebank.service.JwtUtil;
import com.fortunebank.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true") // ✅ for React frontend
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    // ✅ Register endpoint
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest req) {
        if (userService.findByUsername(req.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        // Create new user (default: not admin)
        User newUser = userService.createUser(req.getUsername(), req.getPassword());
        String token = jwtUtil.generateToken(newUser.getUsername());

        // Include admin flag in response
        return ResponseEntity.ok(new AuthResponse(token, newUser.isAdmin()));
    }

    // ✅ Login endpoint
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest req) {
        return userService.findByUsername(req.getUsername())
                .map(user -> {
                    if (userService.checkPassword(user, req.getPassword())) {
                        String token = jwtUtil.generateToken(user.getUsername());
                        boolean isAdmin = user.isAdmin();
                        return ResponseEntity.ok(new AuthResponse(token, isAdmin));
                    } else {
                        return ResponseEntity.status(401).body("Invalid credentials");
                    }
                })
                .orElse(ResponseEntity.status(401).body("Invalid credentials"));
    }
}
