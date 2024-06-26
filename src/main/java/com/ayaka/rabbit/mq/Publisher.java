package com.ayaka.rabbit.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
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
        Message message = MessageBuilder.withBody(s.getBytes(StandardCharsets.UTF_8)).build();
        String exchangeName = "xsj.amq.direct";
        //String message = "hello rabbitMq"+s;
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
        String exchangeName = "test.xsj.topic";
        String message = "hello rabbitMq topic";
        Map<String,Object> map = new HashMap();
        map.put("xsj",message);
        //routingKey = yx.#时：xsj.amq.topic交换机将消息发到yx.开头的队列里
        //routingKey = #.mihoyo时：xsj.amq.topic交换机将消息发到以.mihoyo结尾的队列里
        rabbitTemplate.convertAndSend(exchangeName,"test.#",map);
    }

    public void sendMessageReturn(){
        //创建对象  里面有一个ID 代码消息唯一性
        CorrelationData cd = new CorrelationData();
        cd.getFuture().addCallback(new ListenableFutureCallback<CorrelationData.Confirm>() {
            @Override
            public void onFailure(Throwable throwable) {
                //发送信息异常 基本不触发
                log.error("RabbitMq发送信息失败",throwable);
            }

            @Override
            public void onSuccess(CorrelationData.Confirm confirm) {
                //收到mq异步通知的消息发送结果
                if (confirm.isAck()){ //ack发送成功 nack发送失败
                    //发送成功
                    log.info("ACK----RabbitMq发送信息成功");
                }else{
                    //发送失败
                    log.error("NACK----RabbitMq发送信息失败,说明：{}",confirm.getReason());
                }
            }
        });
        rabbitTemplate.convertAndSend("test.xsj.topic","test.#","map",cd);
    }
}
