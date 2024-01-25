package ua.kpi.fict.chaban.rabbitmessageservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.kpi.fict.chaban.rabbitmessageservice.entities.User;
import ua.kpi.fict.chaban.rabbitmessageservice.publisher.RabbitMQJsonProducer;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class JsonMessageController {
    private final RabbitMQJsonProducer rabbitMQJsonProducer;

    @PostMapping("/publish")
    public ResponseEntity<String> sendJsonMessage(@RequestBody User user) {
        rabbitMQJsonProducer.sendJsonMessage(user);
        return ResponseEntity.ok("JSON message sent to the RabbitMQ Successfully");
    }
}
