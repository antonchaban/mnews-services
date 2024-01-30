//package com.a.chaban.uservice.userservice.controllers;
//
//import com.a.chaban.uservice.userservice.dtos.UserDto;
//import com.a.chaban.uservice.userservice.services.RabbitMQUserProducer;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api")
//public class JsonMessageController {
//    private final RabbitMQUserProducer producer;
//
//    public JsonMessageController(RabbitMQUserProducer producer) {
//        this.producer = producer;
//    }
//
//    @PostMapping("/publish")
//    public ResponseEntity<String> sendJsonMessage(@RequestBody UserDto user) {
//        producer.sendJsonMessage(user);
//        return ResponseEntity.ok("JSON message sent to the RabbitMQ Successfully");
//    }
//}
