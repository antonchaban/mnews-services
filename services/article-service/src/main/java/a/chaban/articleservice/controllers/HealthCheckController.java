package a.chaban.articleservice.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/articles/health")
public class HealthCheckController {

    @GetMapping
    public ResponseEntity<String> getHealth() {
        return ResponseEntity.ok(null);
    }
}
