package com.a.chaban.uservice.userservice.config;


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
    private String queueName;

    @Value("${rabbitmq.user.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.user.routing.key.name}")
    private String routingKey;

    @Bean
    public Queue userQueue() {
        return new Queue(queueName);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchangeName);
    }


    @Bean
    public Binding userBinding() {
        return BindingBuilder
                .bind(userQueue())
                .to(exchange())
                .with(routingKey);
    }

//    ########

    @Value("${rabbitmq.delete.queue.name}")
    private String deleteQueue;

    @Value("${rabbitmq.delete.exchange.name}")
    private String deleteExchangeName;

    @Value("${rabbitmq.delete.routing.key.name}")
    private String deleteRoutingKey;

    @Bean
    public Queue deleteQueue() {
        return new Queue(deleteQueue);
    }

    @Bean
    public TopicExchange delExchange() {
        return new TopicExchange(deleteExchangeName);
    }


    @Bean
    public Binding delBinding() {
        return BindingBuilder
                .bind(deleteQueue())
                .to(delExchange())
                .with(deleteRoutingKey);
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
