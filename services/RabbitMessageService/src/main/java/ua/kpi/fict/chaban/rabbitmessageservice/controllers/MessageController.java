package ua.kpi.fict.chaban.rabbitmessageservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.kpi.fict.chaban.rabbitmessageservice.publisher.RabbitMQProducer;


import java.util.List;

@RestController
//@CrossOrigin
@RequestMapping("/api")
@RequiredArgsConstructor
public class MessageController {
/*
    @Autowired
    private MessageServiceImpl messageService;

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    @GetMapping("/messages/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable Long id) {
        return ResponseEntity.ok(messageService.getMessageById(id));
    }*/

    private final RabbitMQProducer rabbitMQProducer;

    @GetMapping("/publish")
    public ResponseEntity<String> sendMessage(@RequestParam("message") String message) {
        rabbitMQProducer.send(message);
        return ResponseEntity.ok("Message sent to the RabbitMQ Successfully");
    }
}
