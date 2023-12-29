package com.a.chaban.uservice.userservice.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users/health")
public class HealthCheckController {

    @GetMapping
    public ResponseEntity<String> getHealth() {
        return ResponseEntity.ok(null);
    }
}
