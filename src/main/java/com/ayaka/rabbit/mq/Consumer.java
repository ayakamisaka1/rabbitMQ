package com.ayaka.rabbit.mq;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

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

    /**
     * 注解形式去创建队列与交换机
     * @param msg
     */
    @RabbitListener(bindings = @QueueBinding(value = @Queue(name = "test.xsj.topic.queue"),
            exchange = @Exchange(name = "test.xsj.topic", type = ExchangeTypes.TOPIC),
            key = {"test.#"}))
    public void listenQueue(Map<String, Object> msg) {
        log.info("收到消息");
        Set<Map.Entry<String, Object>> entries = msg.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
        log.info("消息处理完成");
    }
}
