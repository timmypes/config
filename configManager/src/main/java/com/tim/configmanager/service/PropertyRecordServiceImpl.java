package com.tim.configmanager.service;

import com.tim.configmanager.dao.PropertyRecordDao;
import com.tim.configmanager.entity.PropertyRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: li si
 */
@Service
public class PropertyRecordServiceImpl implements PropertyRecordService {
    @Autowired
    private PropertyRecordDao propertyRecordDao;

    @Override
    public boolean addPropertyRecord(PropertyRecord propertyRecord) {
        return propertyRecordDao.addPropertyRecord(propertyRecord) == 1;
    }

    @Override
    public List<PropertyRecord> findRecordListByPropId(String propId, Integer page, Integer pageSize) {
        Integer start = (page - 1) * pageSize;
        return propertyRecordDao.findRecordListByPropId(propId, start, pageSize);
    }

    @Override
    public PropertyRecord getRecordById(String recordId) {
        return propertyRecordDao.getRecordById(recordId);
    }

    @Override
    public Long countDeliveryRecord(String propId) {
        return propertyRecordDao.countDeliveryRecord(propId);
    }


}
