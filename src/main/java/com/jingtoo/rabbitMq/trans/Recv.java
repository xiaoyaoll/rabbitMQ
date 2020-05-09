package com.jingtoo.rabbitMq.trans;

import com.jingtoo.rabbitMq.utils.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author liulei
 * @date 2020/5/9
 */
public class Recv {

    public static final String QUEUE_NAME="queue_confirm";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtils.getConnection();
        //获取通道
        final Channel channel = connection.createChannel();

        channel.basicQos(1);
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        System.out.println("hello");
        DefaultConsumer consumer = new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                channel.basicAck(envelope.getDeliveryTag(),true);
                System.out.println(new String(body));


            }
        };

        channel.basicConsume(QUEUE_NAME,false,consumer);

//        channel.close();
//        connection.close();

    }

}
