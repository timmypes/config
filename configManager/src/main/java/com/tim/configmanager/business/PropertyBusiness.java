package com.tim.configmanager.business;


import com.tim.configmanager.entity.Property;
import com.tim.configmanager.form.PropertyForm;
import com.tim.configmanager.vo.PropertyVO;

import java.util.List;
import java.util.Map;

/**
 * @description: 属性相关业务逻辑
 * @author: li si
 */
public interface PropertyBusiness {
    List<PropertyVO> findPropertyListByAppName(String name);

    boolean addProperty(PropertyForm propertyForm);

    boolean updateProperty(PropertyForm propertyForm);

    boolean deleteProperty(PropertyForm propertyForm);

    boolean setPreviousVersion(String recordId);

    Map<String, Object> findPropertyList(Integer page, Integer pageSize);

}
