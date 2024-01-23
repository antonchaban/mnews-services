package ua.kpi.fict.chaban.rabbitmessageservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kpi.fict.chaban.rabbitmessageservice.entities.Message;
import ua.kpi.fict.chaban.rabbitmessageservice.repositories.MessageRepo;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageRepo messageRepo;

    @Override
    public void saveMessage(Message message) {
        messageRepo.save(message);
    }

    @Override
    public Message getMessageById(Long id) {
        return messageRepo.findById(id).get();
    }

    @Override
    public List<Message> getAllMessages() {
        return messageRepo.findAll();
    }
}
