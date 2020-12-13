package com.tim.configmanager.dao;


import com.tim.configmanager.entity.PropertyRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description:
 * @author: li si
 */

@Repository
public interface PropertyRecordDao {
    int addPropertyRecord(@Param("propertyRecord") PropertyRecord propertyRecord);

    List<PropertyRecord> findRecordListByPropId(@Param("propId") String propId, @Param("start") Integer start, @Param("size") Integer size);

    PropertyRecord getRecordById(@Param("recordId") String recordId);

    Long countDeliveryRecord(@Param("propId") String propId);
}
