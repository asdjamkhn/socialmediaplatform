package com.example.SocialMediaPlatform.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Value("${rabbitmq.queue.name}")
    private String rmqQueue;

    @Value("${rabbitmq.exchange.name}")
    private String rmqExchange;

    @Value("${rabbitmq.routing.key.name}")
    private String rmqRoutingKey;

    @Bean
    public TopicExchange smpTopicExchange() {
        return new TopicExchange(rmqExchange);
    }

    @Bean
    public Queue smpQueue() {
        return new Queue(rmqQueue, true);
    }

    @Bean
    public Binding fcmBinding(Queue rmqQueue, TopicExchange smpTopicExchange) {
        return BindingBuilder.bind(rmqQueue).to(smpTopicExchange).with(rmqRoutingKey);
    }
}
