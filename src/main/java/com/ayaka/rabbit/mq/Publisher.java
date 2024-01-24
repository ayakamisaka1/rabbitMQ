package com.ayaka.rabbit.mq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class Publisher {

    private RabbitTemplate rabbitTemplate;

    public Publisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * 点对点模式  消息直接发到队列里面
     * @param s
     */
    public void sendMessage(String s){
        String queueName = "rabbitMqTest";
        String message = "hello rabbitMq"+s;
        rabbitTemplate.convertAndSend(queueName,message);
    }

    /**
     * 广播模式 消息发到exchangeFanout交换机  通过路由广播到关联这个交换机的所有队列里
     * @param s
     */
    public void sendMessageFanout(String s){
        String exchangeName = "xsj.amq";
        String message = "hello rabbitMq"+s;
        rabbitTemplate.convertAndSend(exchangeName,null,message);
    }

    /**
     * 定向路由模式 通过路由routingKey匹配  交换机将消息推到所有的匹配队列里
     * @param s
     */
    public void sendMessageQueueDirect(String s){
        String exchangeName = "xsj.amq.direct";
        String message = "hello rabbitMq"+s;
        //routingKey = 1时：xsj.amq.direct交换机将消息推到路由规则为1的消息队列里
        //routingKey = 2时：xsj.amq.direct交换机将消息推到路由规则为2的消息队列里
        //routingKey = 3时：xsj.amq.direct交换机将消息推到路由规则为3的消息队列里
        rabbitTemplate.convertAndSend(exchangeName,"3",message);
    }

    /**
     * 话题模式 通过路由routingKey匹配  交换机将消息推到所有满足条件的队列里
     * @param s
     */
    public void sendMessageQueueTopic(String s){
        String exchangeName = "xsj.amq.topic";
        String message = "hello rabbitMq"+s;
        //routingKey = yx.#时：xsj.amq.topic交换机将消息发到yx.开头的队列里
        //routingKey = #.mihoyo时：xsj.amq.topic交换机将消息发到以.mihoyo结尾的队列里
        rabbitTemplate.convertAndSend(exchangeName,"yx.mihoyo",message);
    }
}
