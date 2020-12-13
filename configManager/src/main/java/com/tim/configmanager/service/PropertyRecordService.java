package com.tim.configmanager.service;

import com.tim.configmanager.entity.PropertyRecord;

import java.util.List;

/**
 * @description:
 * @author: li si
 */
public interface PropertyRecordService {
    boolean addPropertyRecord(PropertyRecord propertyRecord);

    List<PropertyRecord> findRecordListByPropId(String propId, Integer page, Integer pageSize);

    PropertyRecord getRecordById(String recordId);

    Long countDeliveryRecord(String propId);
}
