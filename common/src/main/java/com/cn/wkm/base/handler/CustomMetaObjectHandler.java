package com.cn.wkm.base.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.cn.wkm.base.entity.Base;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @ClassName CustomMetaObjectHandler
 * @Description 自定义sql字段填充器，自动填充创建修改相关字段
 * @Author kunmin.wang
 * @Date 2021/7/31 15:17
 * @ModifyDate 2021/7/31 15:17
 */
@Log4j2
public class CustomMetaObjectHandler implements MetaObjectHandler {
    private static final String CREATE_BY = "createBy";
    private static final String CREATE_BY_NAME = "createByName";
    private static final String CREATE_AT = "createAt";
    private static final String UPDATE_BY = "updateBy";
    private static final String UPDATE_BY_NAME = "updateByName";
    private static final String UPDATE_AT = "updateAt";
    private static final String DELETED = "deleted";

//    @Autowired
//    private TokenManager tokenManager;

    @Override
    public void insertFill(final MetaObject metaObject) {
        final Base base = getBase();
        if (base != null) {
//            // 子账号ID
//            final Long subAccountId = base.getSubAccountId();
            // sys_user 表 id 登录人ID
            final Long userId = base.getUserId();
            final String accountName;
            if (Objects.equals(base.getAccountType(), 0)) {
                accountName = "管理员";
            } else {
                accountName = base.getAccountName();
            }
            this.strictInsertFill(metaObject, CREATE_BY, Long.class, userId);
            this.strictInsertFill(metaObject, CREATE_BY_NAME, String.class, accountName);
            this.strictUpdateFill(metaObject, UPDATE_BY, Long.class, userId);
            this.strictUpdateFill(metaObject, UPDATE_BY_NAME, String.class, accountName);
        } else {
            Object createByValue = getFieldValByName(CREATE_BY, metaObject);
            Object updateByValue = getFieldValByName(UPDATE_BY, metaObject);
            if(createByValue == null){
                this.strictInsertFill(metaObject, CREATE_BY, Long.class, -1L);
            }
            if(updateByValue == null){
                this.strictUpdateFill(metaObject, UPDATE_BY, Long.class, -1L);
            }
        }
        final LocalDateTime now = LocalDateTime.now();
        this.strictInsertFill(metaObject, CREATE_AT, LocalDateTime.class, now);
        this.strictUpdateFill(metaObject, UPDATE_AT, LocalDateTime.class, now);
        this.strictInsertFill(metaObject, DELETED, Byte.class, (byte) 0);
    }

    @Override
    public void updateFill(final MetaObject metaObject) {
        final Base base = getBase();
        if (base != null) {
            final String accountName;
            if (Objects.equals(base.getAccountType(), 0)) {
                accountName = "管理员";
            } else {
                accountName = base.getAccountName();
            }
            this.setFieldValByName(UPDATE_BY, base.getUserId(), metaObject);
            this.setFieldValByName(UPDATE_BY_NAME, accountName, metaObject);
        } else {
            this.setFieldValByName(UPDATE_BY, -1L, metaObject);
        }
        this.setFieldValByName(UPDATE_AT, LocalDateTime.now(), metaObject);
    }

    private Base getBase(){
//        tokenManager
        return null;
    }
}