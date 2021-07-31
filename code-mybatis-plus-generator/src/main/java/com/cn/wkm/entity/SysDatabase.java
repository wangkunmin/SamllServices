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
 * 数据库连接配置表
 * </p>
 *
 * @author wkm
 * @since 2021-07-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_database")
@ApiModel(value="SysDatabase对象", description="数据库连接配置表")
public class SysDatabase extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "数据源编码（自动生成且唯一）")
    @TableField("db_code")
    private String dbCode;

    @ApiModelProperty(value = "当前用户数（主账号）")
    @TableField("current_size")
    private Long currentSize;

    @ApiModelProperty(value = "最大用户数（主账号）")
    @TableField("max_size")
    private Long maxSize;

    @ApiModelProperty(value = "IP")
    @TableField("ip")
    private String ip;

    @ApiModelProperty(value = "端口")
    @TableField("port")
    private Integer port;

    @ApiModelProperty(value = "数据库名")
    @TableField("db_name")
    private String dbName;

    @ApiModelProperty(value = "登录名")
    @TableField("user_name")
    private String userName;

    @ApiModelProperty(value = "密码")
    @TableField("password")
    private String password;

    @ApiModelProperty(value = "是否达到上限 0：未达到上限 1：达到上限")
    @TableField("is_full")
    private Integer isFull;


}
