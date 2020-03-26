package com.jingtoo.rabbitMq.publishSubscribe;

import com.jingtoo.rabbitMq.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author liulei
 * @date 2020/3/13
 */
public class Send {

    private static final String EXCHANGE="exchange_test";
    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();
        //每次给消费者发一个消息
        channel.basicQos(1);

        //声明交换机
        channel.exchangeDeclare(EXCHANGE, "fanout");
        //发送消息
        String msg="HELLO WORLD";
        channel.basicPublish(EXCHANGE,"",null,msg.getBytes());

        channel.close();
        connection.close();
    }


}
