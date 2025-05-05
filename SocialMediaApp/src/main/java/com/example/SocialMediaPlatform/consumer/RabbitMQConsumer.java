package com.example.SocialMediaPlatform.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitMQConsumer{

    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void consumeMessage(String message){
        log.info("The message is {} " ,  message);
//        System.out.println("The message is " +  message);
    }
}
