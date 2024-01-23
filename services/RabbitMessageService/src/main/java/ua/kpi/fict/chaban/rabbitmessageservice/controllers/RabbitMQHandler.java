package ua.kpi.fict.chaban.rabbitmessageservice.controllers;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.kpi.fict.chaban.rabbitmessageservice.entities.Message;
import ua.kpi.fict.chaban.rabbitmessageservice.services.MessageService;

import java.time.Instant;

@Component
@EnableRabbit
public class RabbitMQHandler {
    @Autowired
    private MessageService messageService;

    @RabbitListener(queues = {"article-queue"})
    public void handleMessage(String message) {
        var msg = new Message();
        msg.setMessageText(message);
        msg.setMessageDate(Instant.now());
        messageService.saveMessage(msg);
    }
}
