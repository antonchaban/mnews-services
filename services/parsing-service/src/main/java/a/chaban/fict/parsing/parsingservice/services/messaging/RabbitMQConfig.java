package a.chaban.fict.parsing.parsingservice.services.messaging;


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

    @Value("${rabbitmq.article.queue.name}")
    private String queueName;

    @Value("${rabbitmq.article.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.article.routing.key.name}")
    private String routingKey;

    @Bean
    public Queue articleQueue() {
        return new Queue(queueName);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchangeName);
    }


    @Bean
    public Binding userBinding() {
        return BindingBuilder
                .bind(articleQueue())
                .to(exchange())
                .with(routingKey);
    }

    // Parsing config

    @Value("${rabbitmq.parsing.queue.name}")
    private String parsingQueueName;

    @Value("${rabbitmq.parsing.exchange.name}")
    private String parsingExchangeName;

    @Value("${rabbitmq.parsing.routing.key.name}")
    private String parsingRoutingKey;

    @Bean
    public Queue parsingQueue() {
        return new Queue(parsingQueueName);
    }

    @Bean
    public TopicExchange parsingExchange() {
        return new TopicExchange(parsingExchangeName);
    }

    @Bean
    public Binding parsingBinding() {
        return BindingBuilder
                .bind(parsingQueue())
                .to(parsingExchange())
                .with(parsingRoutingKey);
    }


    // Sending translated articles created by users to article service

    @Value("${rabbitmq.article-crud.queue.name}")
    private String articleCrudQueueName;

    @Value("${rabbitmq.article-crud.exchange.name}")
    private String articleCrudExchangeName;

    @Value("${rabbitmq.article-crud.routing.key.name}")
    private String articleCrudRoutingKey;

    @Bean
    public Queue articleCrudQueue() {
        return new Queue(articleCrudQueueName);
    }

    @Bean
    public TopicExchange articleCrudExchange() {
        return new TopicExchange(articleCrudExchangeName);
    }

    @Bean
    public Binding articleCrudBinding() {
        return BindingBuilder
                .bind(articleCrudQueue())
                .to(articleCrudExchange())
                .with(articleCrudRoutingKey);
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
