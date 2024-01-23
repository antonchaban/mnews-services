package ua.kpi.fict.chaban.rabbitmessageservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.fict.chaban.rabbitmessageservice.entities.Message;

import java.util.List;

@Repository
public interface MessageRepo extends JpaRepository<Message, Long> {
}
