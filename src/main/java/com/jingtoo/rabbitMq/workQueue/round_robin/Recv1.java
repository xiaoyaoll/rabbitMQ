package com.jingtoo.rabbitMq.workQueue.round_robin;

import com.jingtoo.rabbitMq.utils.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author liulei
 * @date 2020/3/13
 */
public class Recv1 {

    private static final String WORK_QUEUE="work_queue_test";

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();

        //声明队列
        channel.queueDeclare(WORK_QUEUE,false,false,false,null);

        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("[1]:" + new String(body));
            }
        };
        //自动消息应答
        channel.basicConsume(WORK_QUEUE,true,consumer);

    }
}
