package com.tim.config.service;

import com.alibaba.fastjson.JSON;
import com.tim.config.entity.BeanInjectionData;
import com.tim.config.entity.Property;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * @description: bean属性注入服务
 * @author: li si
 * @create: 2020-09-19 16:01
 */
@Service
public class InjectPropertyServiceImpl implements Watcher, InjectPropertyService {
    @Value("${config.zkAddress}")
    private String zkAddress;

    private String appName;

    private String appZkPath;

    private ZooKeeper zkClient;

    private Map<String, String> propertyMap;

    private Map<String, BeanInjectionData> beanInjectionMap;

    private static final String APP_NAME_ZOOKEEPER_PATH = "/config/appName/";

    @Autowired
    private PropertyService propertyService;

    @Override
    public void process(WatchedEvent watchedEvent) {
        try {
            this.propertyMap = this.getPropertyMapByAppName(this.appName);
            String version = new String(zkClient.getData(this.appZkPath, this, null), "UTF-8");
            System.out.println("更新后version:" + version);
            this.injectProperty();
        }catch (Exception e){
            System.out.println("=========获取版本号失败===========");
            e.printStackTrace();
        }
    }

    @Override
    public void start(String appName, Map<String, BeanInjectionData> beanInjectionMap) {
        this.appName = appName;
        // 属性版本信息在zk的路径为 /config/appName/应用名
        this.appZkPath = APP_NAME_ZOOKEEPER_PATH + this.appName;
        this.beanInjectionMap = beanInjectionMap;
        try {
            CountDownLatch countDownLatch = new CountDownLatch(1);
            this.zkClient = new ZooKeeper(zkAddress, 5000, (watchedEvent -> {
                System.out.println("create successfully");
                countDownLatch.countDown();
            }));
            countDownLatch.await();
            // 如果zk上没有该应用节点，则创建一个
            Stat stat = zkClient.exists(this.appZkPath, false);
            if(stat == null){
                zkClient.create(this.appZkPath, "-1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
            this.propertyMap = this.getPropertyMapByAppName(this.appName);
            String version = new String(zkClient.getData(this.appZkPath, this, null), "UTF-8");
            System.out.println("初始version: " + version);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 对特定bean实例的所有被@Conf注解的field进行注入
     * @param beanName
     * @param bean
     */
    @Override
    public void initBeanProperty(String beanName, Object bean) {
        if(this.beanInjectionMap.containsKey(beanName)){
            BeanInjectionData beanInjectionData = this.beanInjectionMap.get(beanName);
            beanInjectionData.setObject(bean);
            this.injectProperty(beanInjectionData);
        }
    }


    /**
     * 根据应用名，取出该应用下配置的所有属性
     * @param appName
     * @return
     */
    private Map<String, String> getPropertyMapByAppName(String appName){
        Map<String, String> propertyMap = new HashMap<>();
        List<Property> propertyList = propertyService.findPropertyListByAppName(appName);
        if(!CollectionUtils.isEmpty(propertyList)){
            propertyList.forEach(property -> propertyMap.put(property.getPropName(), property.getPropValue()));
        }
        return propertyMap;
    }

    /**
     * 对特定bean实例的所有被@Conf注解的field进行注入
     * @param beanInjectionData
     */
    private void injectProperty(BeanInjectionData beanInjectionData){
        if(CollectionUtils.isEmpty(this.propertyMap)){
            return;
        }
        if(beanInjectionData.isValid()){
            beanInjectionData.getFields().forEach(fieldInjectionData -> {
                String propValue = this.propertyMap.get(fieldInjectionData.getPropName());
                if(propValue != null){
                    this.doInjectProperty(fieldInjectionData.getField(), propValue, beanInjectionData.getObject());
                }
            });
        }
    }

    /**
     * 对所有的field进行注入
     * 遍历beanInjectionMap中的每一个要注入的field，从propertyMap中取出对应的值注入进去
     */
    private void injectProperty(){
        if(CollectionUtils.isEmpty(this.beanInjectionMap) || CollectionUtils.isEmpty(this.propertyMap)){
            return;
        }
        this.beanInjectionMap.forEach((beanName, beanInjectionData) -> {
            if(beanInjectionData.isValid()){
                beanInjectionData.getFields().forEach(fieldInjectionData -> {
                    String propValue = this.propertyMap.get(fieldInjectionData.getPropName());
                    if(propValue != null){
                        this.doInjectProperty(fieldInjectionData.getField(), propValue, beanInjectionData.getObject());
                    }
                });
            }
        });
    }

    /**
     * 将字符串类型的值（oriValue)转换成目标成员的类型
     * 目前支持转换成Double,Integer,Long这几个基本类型
     * 后续如需对支持的类型进行扩展，直接在里面增加if-else语句即可
     * 如果要转换的目标类型不在上述类型中，则认为要转换的是JSON字符串，将其转换成对应的类型（JSON转换成Map或者JavaBean）
     *
     * @param fieldToInject
     * @param oriValue
     * @param bean
     */
    private void doInjectProperty(Field fieldToInject, String oriValue, Object bean) {
        try {
            Object targetValue;
            String fieldSimpleName = fieldToInject.getType().getSimpleName();
            if ("String".equals(fieldSimpleName)) {
                targetValue = oriValue;
            } else if ("Double".equals(fieldSimpleName)) {
                targetValue = Double.parseDouble(oriValue);
            } else if ("Integer".equals(fieldSimpleName)) {
                targetValue = Integer.parseInt(oriValue);
            } else if ("Long".equals(fieldSimpleName)) {
                targetValue = Long.parseLong(oriValue);
            } else {
                targetValue = JSON.parseObject(oriValue, fieldToInject.getType());
            }
            fieldToInject.setAccessible(true);
            fieldToInject.set(bean, targetValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
