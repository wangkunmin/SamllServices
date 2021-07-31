package com.cn.wkm.base.entity;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName BasePage
 * @Description 分页基础类
 * @Author kunmin.wang
 * @Date 2021/7/31 16:19
 * @ModifyDate 2021/7/31 16:19
 */
@ApiModel
public class BasePage extends Base {
    /**
     * 页码
     */
    @ApiModelProperty(value = "页码")
    protected Integer pageNum;
    /**
     * 每页大小
     */
    @ApiModelProperty(value = "每页大小")
    @Getter
    @Setter
    protected Integer pageSize;

    public Integer getPageNum() {
        if (pageNum == null || pageNum < 1) {
            this.pageNum = 1;
        }
        return this.pageNum;
    }

    public void setPageNum(Integer pageNum) {
        if (pageNum == null || pageNum < 1) {
            this.pageNum = 1;
            return;
        }
        this.pageNum = pageNum;
    }

    public Page<Object> startPage() {
        return PageHelper.startPage(getPageNum(), getPageSize());
    }
}