package com.tim.configmanager.business;

import com.tim.configmanager.vo.PropertyRecordVO;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: li si
 */
public interface PropertyRecordBusiness {
    Map<String, Object> findRecordListByPropId(String propId, Integer page, Integer pageSize);
}
