package com.cn.wkm.base.handler;

import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.google.common.collect.Lists;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;

import java.util.List;

/**
 * @ClassName CustomTenantHandler
 * @Description TODO
 * @Author kunmin.wang
 * @Date 2021/7/31 15:13
 * @ModifyDate 2021/7/31 15:13
 */
public class CustomTenantHandler implements TenantLineHandler {
    //不走租户的表
    private static List<String> IgnoreTable = Lists.newArrayList(
            "sys_database"
    );
    @Override
    public Expression getTenantId() {
        return new LongValue(returnTenantId());
    }

    //租户字段
    @Override
    public String getTenantIdColumn() {
        return "account_id";
    }

    // 这是 default 方法,默认返回 false 表示所有表都需要拼多租户条件
    @Override
    public boolean ignoreTable(String tableName) {
        return IgnoreTable.contains(tableName);
    }

    /**
     * 从请求中获取到token，从token中解析出tenantId
     *
     * @return 租户ID
     */
    public Long returnTenantId() {
        //初始化值，保存程序正常启动
        Long tenantId = 1L;
        //从请求头中获取token
        return tenantId;
    }
}