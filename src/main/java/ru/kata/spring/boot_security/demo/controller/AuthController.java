package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.util.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/header")
    public ResponseEntity<String> authHeader(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        return ResponseEntity.ok("success: " + authHeader);
    }

    @GetMapping("/param")
    public ResponseEntity<String> authParam(@RequestParam("token") String token) {
        return ResponseEntity.ok("Token Parameter: " + token);
    }

    @GetMapping("/generateToken")
    public ResponseEntity<String> generateToken(@RequestParam("password") String password) {
        String token = jwtUtil.generateToken(password);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/jwt")
    public ResponseEntity<String> authJwt(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt) {
        String password = jwtUtil.extractPassword(jwt);

        if (jwtUtil.validateToken(jwt, password)) {
            return ResponseEntity.ok("JWT Valid: " + jwt + " for user: " + password);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid JWT token");
        }
    }
}