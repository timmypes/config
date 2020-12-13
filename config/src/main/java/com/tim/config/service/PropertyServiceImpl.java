package com.tim.config.service;

import com.tim.config.dao.PropertyDao;
import com.tim.config.entity.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: 属性相关服务
 * @author: li si
 */

@Service
public class PropertyServiceImpl implements PropertyService{
    @Autowired
    private PropertyDao propertyDao;

    @Override
    public List<Property> findPropertyListByAppName(String appName) {
        return propertyDao.findPropertyListByAppName(appName);
    }
}
