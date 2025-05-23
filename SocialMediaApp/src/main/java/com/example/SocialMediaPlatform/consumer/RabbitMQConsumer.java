package com.example.SocialMediaPlatform.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;

@Service
@Slf4j
public class RabbitMQConsumer{

    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void consumeMessage(String message) throws IOException {
        log.info("The message is {} " ,  message);
//        System.out.println("The message is " +  message);

        if (message.toLowerCase().contains("asad")) {
            System.out.println("Name 'asad' not allowed in the message!");
            return;
        }

        FileWriter fileWriter = new FileWriter("files/reports.txt");
        fileWriter.write(message);
        fileWriter.close();
    }
}
