package com.tim.config.entity;

import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @description: Bean需要注入的信息
 * @author: li si
 */
public class BeanInjectionData {
    // bean实例
    private Object object;

    // bean中需要被注入的域
    private List<FieldInjectionData> fields;


    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public List<FieldInjectionData> getFields() {
        return fields;
    }

    public void setFields(List<FieldInjectionData> fields) {
        this.fields = fields;
    }

    public BeanInjectionData(List<FieldInjectionData> fields, Object object) {
        this.object = object;
        this.fields = fields;
    }

    public BeanInjectionData(List<FieldInjectionData> fields) {
        this.fields = fields;
    }

    public boolean isValid(){
        return this.object != null && !CollectionUtils.isEmpty(this.fields);
    }
}
