package com.tim.configmanager.service;


import com.tim.configmanager.entity.Property;

import java.util.List;

/**
 * @description: 属性相关服务
 * @author: li si
 */
public interface PropertyService {

    /**
     * 获取应用名下配置的所有属性
     * @param appName
     * @return
     */
    List<Property> findPropertyListByAppName(String appName);

    boolean addProperty(Property property);

    boolean updateProperty(Property property);

    boolean deleteProperty(String propertyId);

    /**
     * 查看是否已经配置过了所属应用为appName，属性名为propName的属性
     * @param appName
     * @param propName
     * @return
     */
    boolean checkPropertyExist(String appName, String propName);

    List<Property> findPropertyList(Integer page, Integer pageSize);

    Long countPropertySums();

    Property getPropertyByPropId(String propId);
}
