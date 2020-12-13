package com.tim.configmanager.service;

import com.tim.configmanager.dao.PropertyDao;
import com.tim.configmanager.entity.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: 属性相关服务
 * @author: li si
 * @create: 2020-09-19 12:21
 */

@Service
public class PropertyServiceImpl implements PropertyService{
    @Autowired
    private PropertyDao propertyDao;

    @Override
    public List<Property> findPropertyListByAppName(String appName) {
        return propertyDao.findPropertyListByAppName(appName);
    }

    @Override
    public boolean addProperty(Property property) {
        return propertyDao.addProperty(property) == 1;
    }

    @Override
    public boolean updateProperty(Property property) {
        return propertyDao.updateProperty(property) == 1;
    }

    @Override
    public boolean deleteProperty(String propId) {
        return propertyDao.deleteProperty(propId) == 1;
    }

    @Override
    public boolean checkPropertyExist(String appName, String propName) {
        Property property = propertyDao.getPropertyByAppNameAndPropName(appName, propName);
        return property != null;
    }

    @Override
    public List<Property> findPropertyList(Integer page, Integer pageSize) {
        int pageStart = (page - 1) * pageSize;
        return propertyDao.findPropertyList(pageStart, pageSize);
    }

    @Override
    public Long countPropertySums() {
        return propertyDao.countPropertySums();
    }

    @Override
    public Property getPropertyByPropId(String propId) {
        return propertyDao.getPropertyByPropId(propId);
    }
}
