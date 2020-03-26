package com.jingtoo.rabbitMq.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author liulei
 * @date 2020/3/12
 */
public class ConnectionUtils {

    public static Connection getConnection() throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();

        //设置ip
        factory.setHost("127.0.0.1");
        //设置端口
        factory.setPort(5672);
        //设置用户
        factory.setUsername("liulei");
        //设置密码
        factory.setPassword("a12345");
        //设置访问库
        factory.setVirtualHost("/liulei_cs01");

        return factory.newConnection();
    }

}
