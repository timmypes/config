package com.tim.configmanager.controller;

import com.tim.configmanager.business.PropertyBusiness;
import com.tim.configmanager.form.PageInfo;
import com.tim.configmanager.form.PropertyForm;
import com.tim.configmanager.vo.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @description: 属性操作
 * @author: li si
 */

@RestController
public class PropertyController {
    @Autowired
    private PropertyBusiness propertyBusiness;

    @RequestMapping("/addProperty")
    public CommonResponse addProperty(@Validated(PropertyForm.AddProperty.class) PropertyForm propertyForm, BindingResult br){
        if(br.hasErrors()){
            return CommonResponse.fail(br.getFieldError().getDefaultMessage());
        }
        boolean result = propertyBusiness.addProperty(propertyForm);
        return result ? CommonResponse.success() : CommonResponse.fail();
    }

    @RequestMapping("/updateProperty")
    public CommonResponse updateProperty(@Validated(PropertyForm.UpdateProperty.class) PropertyForm propertyForm, BindingResult br){
        if(br.hasErrors()){
            return CommonResponse.fail(br.getFieldError().getDefaultMessage());
        }
        boolean result = propertyBusiness.updateProperty(propertyForm);
        return result ? CommonResponse.success() : CommonResponse.fail();
    }

    @RequestMapping("/deleteProperty")
    public CommonResponse deleteProperty(@Validated(PropertyForm.DeleteProperty.class) PropertyForm propertyForm, BindingResult br){
        if(br.hasErrors()){
            return CommonResponse.fail(br.getFieldError().getDefaultMessage());
        }
        boolean result = propertyBusiness.deleteProperty(propertyForm);
        return result ? CommonResponse.success() : CommonResponse.fail();
    }

    @RequestMapping("/setPreviousVersion")
    public CommonResponse setPreviousVersion(String recordId){
        if(StringUtils.isEmpty(recordId)){
            return CommonResponse.fail("版本不能为空");
        }
        boolean result = propertyBusiness.setPreviousVersion(recordId);
        return result ? CommonResponse.success() : CommonResponse.fail();
    }

    @RequestMapping("/findPropertyList")
    public CommonResponse findPropertyList(@Validated PageInfo pageInfo, BindingResult br){
        if(br.hasErrors()){
            return CommonResponse.fail(br.getFieldError().getDefaultMessage());
        }
        return CommonResponse.success(propertyBusiness.findPropertyList(pageInfo.getPage(), pageInfo.getPageSize()));
    }
}
