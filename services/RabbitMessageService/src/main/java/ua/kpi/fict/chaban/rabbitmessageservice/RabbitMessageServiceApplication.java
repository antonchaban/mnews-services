package ua.kpi.fict.chaban.rabbitmessageservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
