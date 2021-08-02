package com.cn.wkm.base.api;

/**
 * @ClassName IErrorCode
 * @Description TODO
 * @Author kunmin.wang
 * @Date 2021/7/31 16:01
 * @ModifyDate 2021/7/31 16:01
 */
public interface IErrorCode {

    /**
     * 错误编码 -1、失败 0、成功
     */
    long getCode();

    /**
     * 错误描述
     */
    String getMsg();
}
