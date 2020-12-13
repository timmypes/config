package com.tim.configmanager.service;

/**
 * @description: zk相关服务
 * @author: li si
 */
public interface ZkService {
    /**
     * 更新zk中应用节点的值，通知监听该节点的监听器执行从数据库拉取最新版本属性值的动作
     * 如果此时zk里没有该节点，说明对应的应用未曾启动过（一旦应用启动，便会在zk中创建对应的节点），跳过更新的操作
     * @param version
     */
    void setVersion(String appName, String version);
}
