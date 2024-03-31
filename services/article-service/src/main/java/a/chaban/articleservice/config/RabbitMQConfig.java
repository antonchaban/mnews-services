package a.chaban.articleservice.config;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.user.queue.name}")
    private String userQueueName;

    @Value("${rabbitmq.user.exchange.name}")
    private String userExchangeName;

    @Value("${rabbitmq.user.routing.key.name}")
    private String userRoutingKey;

    @Bean
    public Queue userQueue() {
        return new Queue(userQueueName);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(userExchangeName);
    }


    @Bean
    public Binding userBinding() {
        return BindingBuilder
                .bind(userQueue())
                .to(exchange())
                .with(userRoutingKey);
    }

    // Article config

    @Value("${rabbitmq.article.queue.name}")
    private String articleQueueName;

    @Value("${rabbitmq.article.exchange.name}")
    private String articleExchangeName;

    @Value("${rabbitmq.article.routing.key.name}")
    private String articleRoutingKey;

    @Bean
    public Queue articleQueue() {
        return new Queue(articleQueueName);
    }

    @Bean
    public TopicExchange articleExchange() {
        return new TopicExchange(articleExchangeName);
    }


    @Bean
    public Binding articleBinding() {
        return BindingBuilder
                .bind(articleQueue())
                .to(articleExchange())
                .with(articleRoutingKey);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
