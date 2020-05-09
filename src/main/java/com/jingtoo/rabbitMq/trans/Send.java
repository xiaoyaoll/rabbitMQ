package com.jingtoo.rabbitMq.trans;

import com.jingtoo.rabbitMq.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author liulei
 * @date 2020/5/9
 */
public class Send  {

    public static final String QUEUE_NAME="queue_confirm";

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = ConnectionUtils.getConnection();
        //获取通道
        Channel channel = connection.createChannel();
        try {

            //创建队列
            channel.queueDeclare(QUEUE_NAME,false,false,false,null);

            channel.basicQos(1);

            channel.confirmSelect();
            //发送消息
            channel.basicPublish("",QUEUE_NAME,null,"HELLO".getBytes());

            if(channel.waitForConfirms()){
                System.out.println("消息已确认");
            }


            channel.close();
            connection.close();
          } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
