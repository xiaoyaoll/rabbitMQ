package com.jingtoo.rabbitMq.publishSubscribe;

import com.jingtoo.rabbitMq.utils.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author liulei
 * @date 2020/3/13
 */
public class Recv2 {

    private static final String QUEUE_NAME="queue_pub_test02";
    private static final String EXCHANGE="exchange_test";
    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = ConnectionUtils.getConnection();

        final Channel channel = connection.createChannel();
        channel.basicQos(1);
        //声明交换机
        channel.exchangeDeclare(EXCHANGE,"fanout");

        //声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        channel.queueBind(QUEUE_NAME,EXCHANGE,"");

        DefaultConsumer consumer = new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("订阅者[2]"+new String(body));
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };

        channel.basicConsume(QUEUE_NAME,false,consumer);


    }
}
