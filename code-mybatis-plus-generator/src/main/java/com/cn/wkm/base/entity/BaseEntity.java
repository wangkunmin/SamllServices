package com.cn.wkm.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @ClassName BaseEntity
 * @Description 数据库实体表基础参数类
 * @Author kunmin.wang
 * @Date 2021/7/31 14:16
 * @ModifyDate 2021/7/31 14:16
 */
@Data
@ApiModel(value="数据库实体表基础参数类", description="数据库实体表基础参数类")
public class BaseEntity  implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_at")
    private LocalDateTime createAt;

    @ApiModelProperty(value = "创建人")
    @TableField("create_by")
    private Long createBy;

    @ApiModelProperty(value = "编辑时间")
    @TableField("update_at")
    private LocalDateTime updateAt;

    @ApiModelProperty(value = "更新人")
    @TableField("update_by")
    private Long updateBy;

    @ApiModelProperty(value = "是否删除 0：未删除 1：删除")
    @TableField("is_delete")
    @TableLogic(value = "0",delval = "1")
    private Integer isDelete;
}