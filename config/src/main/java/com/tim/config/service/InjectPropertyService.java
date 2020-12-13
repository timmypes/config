package com.tim.config.service;

import com.tim.config.entity.BeanInjectionData;

import java.util.Map;

/**
 * @description: bean属性注入服务
 * @author: li si
 * @create: 2020-09-20 10:29
 */
public interface InjectPropertyService {
    /**
     * 初始化zk连接，并获取appName下全量的属性信息
     * @param appName
     * @param beanInjectionMap
     */
    void start(String appName, Map<String, BeanInjectionData> beanInjectionMap);


    /**
     * 在bean实例化后，对bean内被@Conf注解的域进行初始赋值
     * @param beanName
     * @param bean
     */
    void initBeanProperty(String beanName, Object bean);

}
