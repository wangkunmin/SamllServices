package com.cn.wkm.base.config.druid;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.support.http.StatViewServlet;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * @ClassName DruidDataSourceProperties
 * @Description TODO
 * @Author kunmin.wang
 * @Date 2021/8/3 11:50
 * @ModifyDate 2021/8/3 11:50
 */
@Data
@RefreshScope
@Component
@ConfigurationProperties("spring.datasource.druid")
public class DruidDataSourceProperties {
    /**
     * 读取配置文件中数据库的连接信息
     */
    /**
     * 驱动名称
     */
    private String driverClassName;
    /**
     * 地址
     */
    private String url;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;

    // jdbc connection pool
    private int initialSize;
    private int minIdle;
    private int maxActive = 100;
    private long maxWait;
    private long timeBetweenEvictionRunsMillis;
    private long minEvictableIdleTimeMillis;
    private String validationQuery;
    private boolean testWhileIdle;
    private boolean testOnBorrow;
    private boolean testOnReturn;
    private boolean poolPreparedStatements;
    private int maxPoolPreparedStatementPerConnectionSize;

    private boolean useGlobalDataSourceStat;
    private Properties connectProperties;

    // filter
    private Filter filter;
    private String filters;
}