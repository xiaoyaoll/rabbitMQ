package com.jingtoo.rabbitMq.workQueue.fair;

import com.jingtoo.rabbitMq.utils.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author liulei
 * @date 2020/3/13
 */
public class Recv2 {

    private static final String WORK_QUEUE="work_queue_test";

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = ConnectionUtils.getConnection();

        final Channel channel = connection.createChannel();

        //1.限制每次获取一条消息
        int prefetchSize=1;
        channel.basicQos(prefetchSize);

        //声明队列
        channel.queueDeclare(WORK_QUEUE,false,false,false,null);

        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("[2]:" + new String(body));
                //2.手动应答
                channel.basicAck(envelope.getDeliveryTag(),false);

            }
        };
        //3.关闭自动应答
        channel.basicConsume(WORK_QUEUE,false,consumer);

    }
}
