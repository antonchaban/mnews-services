package ua.kpi.fict.chaban.rabbitmessageservice;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RabbitMessageServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitMessageServiceApplication.class, args);
    }

/*    @Value("${spring.rabbitmq.host}")
    private String rabbitHost;

    @Bean
    public Queue articleQueue() {
        return new Queue("article-queue", false);
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory(rabbitHost);
    }*/
}
