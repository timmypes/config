package com.tim.configmanager.business;


import com.tim.configmanager.entity.Property;
import com.tim.configmanager.entity.PropertyRecord;
import com.tim.configmanager.enumeration.OperateType;
import com.tim.configmanager.form.PropertyForm;
import com.tim.configmanager.service.PropertyRecordService;
import com.tim.configmanager.service.PropertyService;
import com.tim.configmanager.service.ZkService;
import com.tim.configmanager.utils.UUIDUtils;
import com.tim.configmanager.vo.PropertyVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
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
public class PropertyBusinessImpl implements PropertyBusiness {
    @Autowired
    private PropertyService propertyService;

    @Autowired
    private PropertyRecordService propertyRecordService;

    @Autowired
    private ZkService zkService;


    @Override
    public List<PropertyVO> findPropertyListByAppName(String name) {
        List<PropertyVO> list = new ArrayList<>();
        List<Property> properties = propertyService.findPropertyListByAppName(name);
        if(!CollectionUtils.isEmpty(properties)){
            properties.forEach(property -> {
                PropertyVO propertyVO = new PropertyVO();
                BeanUtils.copyProperties(property, propertyVO);
                list.add(propertyVO);
            });
        }
        return list;
    }

    @Transactional
    @Override
    public boolean addProperty(PropertyForm propertyForm) {
        // 规定特定应用下不能配置有重复属性名的属性
        if(propertyService.checkPropertyExist(propertyForm.getAppName(), propertyForm.getPropName())){
            return false;
        }
        Property property = new Property();
        BeanUtils.copyProperties(propertyForm, property);
        property.setPropId(UUIDUtils.generateUUID());
        boolean addPropResult = propertyService.addProperty(property);

        PropertyRecord propertyRecord = new PropertyRecord();
        BeanUtils.copyProperties(propertyForm, propertyRecord);
        propertyRecord.setRecordId(UUIDUtils.generateUUID());
        propertyRecord.setOperateType(OperateType.ADD.getValue());
        propertyRecord.setPropId(property.getPropId());
        propertyRecord.setRemark("新增");
        boolean addRecordResult = propertyRecordService.addPropertyRecord(propertyRecord);

        if(addRecordResult && addPropResult) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                @Override
                public void afterCommit() {
                    updateZkVersion(propertyForm.getAppName());
                }
            });
        }
        return addRecordResult && addPropResult;
    }

    @Transactional
    @Override
    public boolean updateProperty(PropertyForm propertyForm) {
        Property property = new Property();
        BeanUtils.copyProperties(propertyForm, property);
        boolean updatePropResult = propertyService.updateProperty(property);

        PropertyRecord propertyRecord = new PropertyRecord();
        BeanUtils.copyProperties(propertyForm, propertyRecord);
        propertyRecord.setRecordId(UUIDUtils.generateUUID());
        propertyRecord.setOperateType(OperateType.EDIT.getValue());
        propertyRecord.setRemark(propertyForm.getRemark());
        boolean addRecordResult = propertyRecordService.addPropertyRecord(propertyRecord);

        if(addRecordResult && updatePropResult) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                @Override
                public void afterCommit() {
                    updateZkVersion(propertyForm.getAppName());
                }
            });
        }

        return addRecordResult && updatePropResult;
    }

    @Transactional
    @Override
    public boolean deleteProperty(PropertyForm propertyForm) {
        Property property = propertyService.getPropertyByPropId(propertyForm.getPropId());
        if(property == null){
            return false;
        }
        boolean deletePropResult = propertyService.deleteProperty(propertyForm.getPropId());

        PropertyRecord propertyRecord = new PropertyRecord();
        propertyRecord.setRecordId(UUIDUtils.generateUUID());
        propertyRecord.setOperateType(OperateType.DELETE.getValue());
        propertyRecord.setRemark("删除");
        boolean addRecordResult = propertyRecordService.addPropertyRecord(propertyRecord);

        if(addRecordResult && deletePropResult) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                @Override
                public void afterCommit() {
                    updateZkVersion(propertyForm.getAppName());
                }
            });
        }
        return deletePropResult && addRecordResult;
    }

    @Transactional
    @Override
    public boolean setPreviousVersion(String recordId) {
        PropertyRecord propertyRecord = propertyRecordService.getRecordById(recordId);
        Property property = new Property();
        BeanUtils.copyProperties(propertyRecord, property);
        boolean updatePropResult = propertyService.updateProperty(property);

        PropertyRecord newRecord = new PropertyRecord();
        BeanUtils.copyProperties(propertyRecord, newRecord);
        newRecord.setRemark("回退版本");
        newRecord.setOperateType(OperateType.ROLL_BACK.getValue());
        newRecord.setRecordId(UUIDUtils.generateUUID());
        boolean addRecordResult = propertyRecordService.addPropertyRecord(newRecord);

        if(addRecordResult && updatePropResult) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                @Override
                public void afterCommit() {
                    updateZkVersion(propertyRecord.getAppName());
                }
            });
        }

        return updatePropResult && addRecordResult;
    }

    @Override
    public Map<String, Object> findPropertyList(Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();

        List<PropertyVO> list = new ArrayList<>();
        List<Property> properties = propertyService.findPropertyList(page, pageSize);
        if(!CollectionUtils.isEmpty(properties)){
            properties.forEach(property -> {
                PropertyVO propertyVO = new PropertyVO();
                BeanUtils.copyProperties(property, propertyVO);
                list.add(propertyVO);
            });
        }
        map.put("list", list);

        Long total = propertyService.countPropertySums();
        map.put("total", total);
        return map;
    }

    private void updateZkVersion(String appName){
        zkService.setVersion(appName, System.currentTimeMillis() + "");
    }
}
