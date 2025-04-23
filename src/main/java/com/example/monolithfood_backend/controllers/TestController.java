package com.example.monolithfood_backend.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {
    @Value("${jwt.secret-key}")
    private String secretKey;
    
    @Value("${jwt.expiration-time}")
    private Long expirationTime;

    @GetMapping
    public ResponseEntity<Map<String, String>> test() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Hello World");
        response.put("secretKey", secretKey);
        response.put("expirationTime", expirationTime.toString());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
