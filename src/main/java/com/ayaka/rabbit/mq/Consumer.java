package com.ayaka.rabbit.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Consumer {
    @RabbitListener(queues = "rabbitMqTest")
    public void listenQueuerabbitMqTest(String message) {
        log.info("监听器1监听到rabbitMqTest队列信息：[ "+message+" ]");
        try {
            System.out.println(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("消息处理完成");
    }
    @RabbitListener(queues = "rabbitMqTest2")
    public void listenQueuerabbitMqTest2(String message) {
        log.info("监听器2监听到rabbitMqTest2队列信息：[ "+message+" ]");
        try {
            System.out.println(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("消息处理完成");
    }
}
