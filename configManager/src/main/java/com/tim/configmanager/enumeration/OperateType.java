package com.tim.configmanager.enumeration;

/**
 * @description: 属性操作类型
 * @author: li si
 * @create: 2020-09-19 10:09
 */
public enum OperateType {
    ADD(0, "创建属性"), EDIT(1, "编辑属性"), DELETE(2, "删除属性"), ROLL_BACK(3, "回退属性");

    private Integer value;

    private String desc;

    OperateType(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
