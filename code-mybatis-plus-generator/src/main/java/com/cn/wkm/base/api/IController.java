package com.cn.wkm.base.api;


/**
 * @ClassName IController
 * @Description TODO
 * @Author kunmin.wang
 * @Date 2021/7/31 15:58
 * @ModifyDate 2021/7/31 15:58
 */
public abstract class IController {
    /**
     * 请求成功
     *
     * @param data 数据内容
     * @param <T>  对象泛型
     * @return ignore
     */
    protected  <T> R<T> success(T data) {
        return R.ok(data);
    }

    /**
     * 请求成功
     *
     * @param <T> 对象泛型
     * @return ignore
     */
    protected <T> R<T> success() {
        return R.ok(null);
    }

    /**
     * 请求失败
     *
     * @param msg 提示内容
     * @return ignore
     */
    protected <T> R<T> failed(String msg) {
        return R.failed(msg);
    }

    /**
     * 请求失败
     *
     * @param errorCode 请求错误码
     * @return ignore
     */
    protected <T> R<Object> failed(IErrorCode errorCode) {
        return R.failed(errorCode);
    }
}
