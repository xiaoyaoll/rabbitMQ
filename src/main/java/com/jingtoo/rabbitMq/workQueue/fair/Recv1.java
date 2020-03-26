package com.jingtoo.rabbitMq.workQueue.fair;

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

        final Channel channel = connection.createChannel();

        int prefetchSize=1;
        channel.basicQos(prefetchSize);



        //声明队列
        channel.queueDeclare(WORK_QUEUE,false,false,false,null);

        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    Thread.sleep(900);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("[1]:" + new String(body));
                //2.手动应答
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };
        //关闭自动消息应答
        channel.basicConsume(WORK_QUEUE,false,consumer);

    }
}
