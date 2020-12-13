package com.tim.configmanager.business;

import com.tim.configmanager.entity.PropertyRecord;
import com.tim.configmanager.service.PropertyRecordService;
import com.tim.configmanager.vo.PropertyRecordVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: li si
 */

@Component
public class PropertyRecordBusinessImpl implements PropertyRecordBusiness {
    @Autowired
    private PropertyRecordService propertyRecordService;

    @Override
    public Map<String, Object> findRecordListByPropId(String propId, Integer page, Integer pageSize) {
        Map<String, Object> returnMap = new HashMap<>();
        List<PropertyRecordVO> recordVOList = new ArrayList<>();
        List<PropertyRecord> records = propertyRecordService.findRecordListByPropId(propId, page, pageSize);
        if(!CollectionUtils.isEmpty(records)){
            records.forEach(record -> {
                PropertyRecordVO propertyRecordVO = new PropertyRecordVO();
                BeanUtils.copyProperties(record, propertyRecordVO);
                recordVOList.add(propertyRecordVO);
            });
        }
        returnMap.put("list", recordVOList);

        Long total = propertyRecordService.countDeliveryRecord(propId);
        returnMap.put("total", total);
        return returnMap;
    }
}
