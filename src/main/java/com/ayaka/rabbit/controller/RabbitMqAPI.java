package com.ayaka.rabbit.controller;

import com.ayaka.rabbit.mq.Publisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class RabbitMqAPI {
    @Resource
    private Publisher publisher;

    @GetMapping("/rabbitMq/sendMessageQueue")
    public void sendMessageQueue(){
        publisher.sendMessage("1");
        publisher.sendMessage("2");
    }

    @GetMapping("/rabbitMq/sendMessageQueueFanout")
    public void sendMessageFanout(){
        publisher.sendMessageFanout("1");
    }

    @GetMapping("/rabbitMq/sendMessageQueueDirect")
    public void sendMessageDirect(){
        publisher.sendMessageQueueDirect("1");
    }

    @GetMapping("/rabbitMq/sendMessageQueueTopic")
    public void sendMessageQueueTopic(){
        publisher.sendMessageQueueTopic("1");
    }
}
