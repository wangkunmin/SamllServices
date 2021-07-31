package com.cn.wkm.base.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

/**
 * @ClassName Base
 * @Description TODO
 * @Author kunmin.wang
 * @Date 2021/7/31 15:20
 * @ModifyDate 2021/7/31 15:20
 */
@ApiModel
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Base {
    /**
     * 主账号ID
     */
    @ApiModelProperty(value = "主账号ID", hidden = true)
    private Long accountId;

    /**
     * 子账户ID
     */
    @ApiModelProperty(value = "子账户ID", hidden = true)
    private Long subAccountId;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名", hidden = true)
    private String accountName;


    @ApiModelProperty(value = "主账户登录名", hidden = true)
    private String mainLoginName;

    /**
     * sys_user表ID 用户ID
     */
    @ApiModelProperty(value = "用户ID", hidden = true)
    private Long userId;

    /**
     * 账户类型 0：主账户 1：子账户
     */
    @ApiModelProperty(value = "账户类型 0：主账户 1：子账户", hidden = true)
    private Integer accountType;

    /**
     * 是否为主账号
     */
    @JsonIgnore
    @ApiModelProperty(value = "是否为主账号", hidden = true)
    private boolean main;

    @JsonIgnore
    public void setAll(final Base base) {
        if (base == null) {
            return;
        }
        setAccountId(base.getAccountId());
        setSubAccountId(base.getSubAccountId());
        setAccountName(base.getAccountName());
        setUserId(base.getUserId());
        setAccountType(base.getAccountType());
        setMainLoginName(base.getMainLoginName());
    }

    @JsonIgnore
    public void setAll(final Map<String, Object> map) {
        if (map == null) {
            return;
        }
        setAccountId(Long.valueOf(map.get("accountId").toString()));
        if (map.get("subAccountId") != null) {
            setSubAccountId(Long.valueOf(map.get("subAccountId").toString()));
        }
        setAccountName((String) map.get("accountName"));
        setUserId(Long.valueOf(map.get("id").toString()));
        setAccountType(Integer.valueOf(map.get("accountType").toString()));
        setMainLoginName((String) map.get("mainLoginName"));
    }

    /**
     * 不可修改的Base
     */
    public static class UnmodifiableBase extends Base {
        public UnmodifiableBase(Long accountId, Long subAccountId, String accountName,String mainLoginName, Long userId, Integer accountType) {
            super(accountId, subAccountId, accountName,mainLoginName, userId, accountType, false);
        }
//
//        @Override
//        public void setAccountId(Long accountId) {
//            throw new BuException(ErrorCode.OPERATION_FAILURE);
//        }
//
//        @Override
//        public void setSubAccountId(Long subAccountId) {
//            throw new BuException(ErrorCode.OPERATION_FAILURE);
//        }
//
//        @Override
//        public void setAccountName(String accountName) {
//            throw new BuException(ErrorCode.OPERATION_FAILURE);
//        }
//
//        @Override
//        public void setUserId(Long userId) {
//            throw new BuException(ErrorCode.OPERATION_FAILURE);
//        }
//
//        @Override
//        public void setAccountType(Integer accountType) {
//            throw new BuException(ErrorCode.OPERATION_FAILURE);
//        }
//
//        @Override
//        public void setAll(Base base) {
//            throw new BuException(ErrorCode.OPERATION_FAILURE);
//        }
//
//        @Override
//        public void setAll(Map<String, Object> map) {
//            throw new BuException(ErrorCode.OPERATION_FAILURE);
//        }
    }

}