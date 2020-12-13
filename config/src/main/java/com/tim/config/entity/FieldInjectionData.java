package com.tim.config.entity;

import java.lang.reflect.Field;

/**
 * @description: bean中需要注入的方法
 * @author: li si
 */
public class FieldInjectionData {

    // 被@Conf注解的域
    private Field field;

    // @Conf中定义的属性名称
    private String propName;

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public String getPropName() {
        return propName;
    }

    public void setPropName(String propName) {
        this.propName = propName;
    }
}
