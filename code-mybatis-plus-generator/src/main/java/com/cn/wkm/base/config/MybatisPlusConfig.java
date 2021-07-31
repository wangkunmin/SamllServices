package com.cn.wkm.base.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.cn.wkm.base.handler.CustomMetaObjectHandler;
import com.cn.wkm.base.handler.CustomTenantHandler;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName MybatisPlusConfig
 * @Description TODO
 * @Author kunmin.wang
 * @Date 2021/7/31 14:54
 * @ModifyDate 2021/7/31 14:54
 */
@Configuration
@MapperScan("com.cn.wkm.**.mapper")
public class MybatisPlusConfig {
    /**
     * 新多租户插件配置,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存万一出现问题
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new TenantLineInnerInterceptor(new CustomTenantHandler()));
        // 如果用了分页插件注意先 add TenantLineInnerInterceptor 再 add PaginationInnerInterceptor
        // 用了分页插件必须设置 MybatisConfiguration#useDeprecatedExecutor = false
//        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }

//   该拦截器和逻辑删除注解@TableLogic联合使用，@TableLogic逻辑删除注解会触发此拦截器，从而对数据进行逻辑删除，即把deleted置为1
//    高版本mybatis-plus不用配置插件了，下面java代码无需编写。
//    @Bean
//    public ISqlInjector sqlInjector() {
//        return new LogicSqlInjector();
//    }

    /**
     * 自定义公共字段自动注入
     */
    @Bean
    public MetaObjectHandler metaObjectHandler() {
        //自定义sql字段填充器，自动填充创建修改相关字段
        return new CustomMetaObjectHandler();
    }
}