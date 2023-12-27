package com.a.chaban.sts.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/secure")
public class HealthCheckController {

    @GetMapping("health")
    public ResponseEntity<String> getHealth() {
        return ResponseEntity.ok(null);
    }
}