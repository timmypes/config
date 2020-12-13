package com.tim.configmanager.service;

import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;

/**
 * @description:
 * @author: li si
 */

@Service
public class ZkServiceImpl implements ZkService , InitializingBean {
    @Value("${config.zkAddress}")
    private String zkAddress;

    private static final String APP_NAME_ZOOKEEPER_PATH = "/config/appName/";

    private ZooKeeper zkClient;

    @Override
    public void setVersion(String appName, String version) {
        try {
            Stat stat = this.zkClient.exists(APP_NAME_ZOOKEEPER_PATH + appName, false);
            if(stat != null) {
                zkClient.setData(APP_NAME_ZOOKEEPER_PATH + appName, version.getBytes(), -1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void afterPropertiesSet() {
        try {
            CountDownLatch countDownLatch = new CountDownLatch(1);
            this.zkClient = new ZooKeeper(zkAddress, 5000, (watchedEvent -> {
                System.out.println("create successfully");
                countDownLatch.countDown();
            }));
            countDownLatch.await();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
