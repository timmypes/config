package com.tim.configmanager.form;

import javax.validation.constraints.NotBlank;

/**
 * @description:
 * @author: li si
 * @create: 2020-09-20 14:53
 */
public class PropertyForm {
    @NotBlank(message = "属性id不能未空", groups = {UpdateProperty.class, DeleteProperty.class})
    private String propId;

    @NotBlank(message = "应用名不能为空", groups = {AddProperty.class})
    private String appName;

    @NotBlank(message = "属性名不能为空", groups = {AddProperty.class, UpdateProperty.class})
    private String propName;

    @NotBlank(message = "属性值不能为空", groups = {AddProperty.class, UpdateProperty.class})
    private String propValue;

    private String instruction;

    private String remark;

    public interface AddProperty{

    }

    public interface UpdateProperty{

    }

    public interface DeleteProperty{

    }

    public String getPropId() {
        return propId;
    }

    public void setPropId(String propId) {
        this.propId = propId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPropName() {
        return propName;
    }

    public void setPropName(String propName) {
        this.propName = propName;
    }

    public String getPropValue() {
        return propValue;
    }

    public void setPropValue(String propValue) {
        this.propValue = propValue;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
