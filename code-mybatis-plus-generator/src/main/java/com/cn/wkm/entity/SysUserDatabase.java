package com.cn.wkm.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cn.wkm.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 账号与数据关系表
 * </p>
 *
 * @author wkm
 * @since 2021-07-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user_database")
@ApiModel(value="SysUserDatabase对象", description="账号与数据关系表")
public class SysUserDatabase extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主账户ID，即租户公司ID")
    @TableField("account_id")
    private Long accountId;

    @ApiModelProperty(value = "sys_database.id")
    @TableField("db_id")
    private Long dbId;

    @ApiModelProperty(value = "sys_database.db_code")
    @TableField("db_code")
    private String dbCode;


}
