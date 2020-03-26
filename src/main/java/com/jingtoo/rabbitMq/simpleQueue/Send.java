package com.jingtoo.rabbitMq.simpleQueue;

import com.jingtoo.rabbitMq.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author liulei
 * @date 2020/3/12
 */
public class Send {

    private static final String QUEUE_NAME="queue_test1";

    public static void main(String[] args) throws IOException, TimeoutException {
        //获得连接
        Connection connection = ConnectionUtils.getConnection();
        //创建频道
        Channel channel = connection.createChannel();
        //对队列声明
        channel.queueDeclare(QUEUE_NAME, false, false,false, null);
        String msg="HELLO WORLD !";
        //发布消息
        channel.basicPublish("",  QUEUE_NAME,null,msg.getBytes());
        channel.close();
        connection.close();
    }




}
