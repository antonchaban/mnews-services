package ua.kpi.fict.chaban.rabbitmessageservice.services;


import org.springframework.stereotype.Service;
import ua.kpi.fict.chaban.rabbitmessageservice.entities.Message;

import java.util.List;

@Service
public interface MessageService {
    void saveMessage(Message message);

    Message getMessageById(Long id);

    List<Message> getAllMessages();
}
