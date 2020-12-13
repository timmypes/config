package com.tim.configmanager.controller;

import com.tim.configmanager.business.PropertyRecordBusiness;
import com.tim.configmanager.form.PageInfo;
import com.tim.configmanager.vo.CommonResponse;
import com.tim.configmanager.vo.PropertyRecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @description: 属性编辑历史
 * @author: li si
 */

@RestController
public class PropertyRecordController {
    @Autowired
    private PropertyRecordBusiness propertyRecordBusiness;

    @RequestMapping("/findRecordListByPropId")
    public CommonResponse findRecordListByPropId(@Validated PageInfo pageInfo, String propId, BindingResult br){
        if(br.hasErrors()){
            return CommonResponse.fail(br.getFieldError().getDefaultMessage());
        }

        if(StringUtils.isEmpty(propId)){
            return CommonResponse.fail("属性id不能未空");
        }
        Map<String, Object> returnMap = propertyRecordBusiness.findRecordListByPropId(propId, pageInfo.getPage(), pageInfo.getPageSize());
        return CommonResponse.success(returnMap);
    }
}
