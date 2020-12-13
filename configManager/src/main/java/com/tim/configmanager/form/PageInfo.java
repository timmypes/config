package com.tim.configmanager.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @description: 分页相关参数
 * @author: li si
 */
public class PageInfo {
    @NotNull(message = "页码不能未空")
    @Min(value = 1, message = "页码不能小于1")
    private Integer page;

    @NotNull(message = "每页展示数不能未空")
    @Min(value = 1, message = "每页展示数不能小于1")
    private Integer pageSize;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
