package com.jingtoo.zookeeper;

import org.apache.zookeeper.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * @author liulei
 * @date 2020/3/25
 */
public class Demo {

    private String host="192.168.25.130:2181";
    private ZooKeeper zooKeeper;

    @Before
    public void getConnect() throws IOException, InterruptedException {

        zooKeeper = new ZooKeeper(host, 2000, new Watcher() {
            public void process(WatchedEvent event) {
                List<String> list = null;
                try {
                    list = zooKeeper.getChildren("/", true);
                    for (String s : list) {
                        System.out.println(s);
                    }
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Test
    public void creatNode() throws IOException, KeeperException, InterruptedException {
        String path = zooKeeper.create("/LIULEI", "NIUBI1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(path);
    }

    @Test
    public void getNode() throws KeeperException, InterruptedException {
        List<String> list = zooKeeper.getChildren("/", true);
        for (String s : list) {
            System.out.println(s);
        }
        Thread.sleep(Long.MAX_VALUE);
    }
}
