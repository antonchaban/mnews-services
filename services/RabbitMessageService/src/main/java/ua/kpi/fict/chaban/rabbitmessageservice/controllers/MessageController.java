package ua.kpi.fict.chaban.rabbitmessageservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.kpi.fict.chaban.rabbitmessageservice.entities.Message;
import ua.kpi.fict.chaban.rabbitmessageservice.services.MessageServiceImpl;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class MessageController {

    @Autowired
    private MessageServiceImpl messageService;

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    @GetMapping("/messages/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable Long id) {
        return ResponseEntity.ok(messageService.getMessageById(id));
    }
}
