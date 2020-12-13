package com.tim.config.service;

import com.tim.config.entity.Property;

import java.util.List;

/**
 * @description: 属性相关服务
 * @author: li si
 * @create: 2020-09-19 12:21
 */
public interface PropertyService {

    /**
     * 获取应用名下配置的所有属性
     * @param appName
     * @return
     */
    List<Property> findPropertyListByAppName(String appName);
}
