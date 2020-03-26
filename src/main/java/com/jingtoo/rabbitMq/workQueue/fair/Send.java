package com.jingtoo.rabbitMq.workQueue.fair;

import com.jingtoo.rabbitMq.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 工作队列
 * @author liulei
 * @date 2020/3/13
 */
public class Send {

    private static final String WORK_QUEUE="work_queue_test";

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();

        //声明队列
        channel.queueDeclare(WORK_QUEUE,false,false,false,null);
        //限制发给每个消费者的消息不超过一条
        int prefetchSize=1;
        channel.basicQos(prefetchSize);

        for (int i = 0; i <50 ; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String msg="HELLO WORKQUEUE "+i;
            //发送消息
            channel.basicPublish("",WORK_QUEUE,null,msg.getBytes());
            System.out.println("send: "+msg);
        }

        channel.close();
        connection.close();
    }


}
