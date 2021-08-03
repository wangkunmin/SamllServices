package com.cn.wkm.base.config.druid;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName DruidConfiguration
 * @Description TODO
 * @Author kunmin.wang
 * @Date 2021/7/30 16:24
 * @ModifyDate 2021/7/30 16:24
 */
@Configuration
@Slf4j
@EnableConfigurationProperties({DruidDataSourceProperties.class})
public class DruidConfiguration {
    /**
     * 默认数据源
     */
    public final static String DEFAULT_DATA_SOURCE = "defaultDataSource";

    @Autowired
    private DruidDataSourceProperties druidDataSourceProperties;

    @Bean
    public ServletRegistrationBean<StatViewServlet> druidServlet() {
        ServletRegistrationBean<StatViewServlet> servletRegistrationBean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
        // IP白名单
        // servletRegistrationBean.addInitParameter("allow", "/*");
        // IP黑名单(共同存在时，deny优先于allow)
        // servletRegistrationBean.addInitParameter("deny", "192.168.1.100");
        //控制台管理用户
        servletRegistrationBean.addInitParameter("loginUsername", "admin");
        servletRegistrationBean.addInitParameter("loginPassword", "admin");
        //是否能够重置数据 禁用HTML页面上的“Reset All”功能
        servletRegistrationBean.addInitParameter("resetEnable", "false");
        return servletRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean<WebStatFilter> webStatFilter() {
        FilterRegistrationBean<WebStatFilter> beanFilter = new FilterRegistrationBean<>();
        beanFilter.setFilter(new WebStatFilter());
        Map<String, String> initParams = new HashMap<>();
        initParams.put("exclusions", "*.js,*.json,*.html,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/static/*,/druid/*,/swagger*,/v2/api-docs");
        beanFilter.setInitParameters(initParams);
        beanFilter.setUrlPatterns(Collections.singletonList("/*"));
        return beanFilter;
    }

    /**
     * 默认数据源（主数据源）
     *
     * @return DataSource
     */
    @Bean(name = DEFAULT_DATA_SOURCE)
    public DataSource druidDataSource() {
        log.info("==================================== InitDatabaseConfig -> dataSource -> 开始创建数据库 ====================================");
        // 数据库连接对象
        Connection connection = null;
        Statement statement = null;
        String url = druidDataSourceProperties.getUrl();
        String driverClassName = druidDataSourceProperties.getDriverClassName();
        String username = druidDataSourceProperties.getUsername();
        String password = druidDataSourceProperties.getPassword();
        try {

            // 如果尝试去连接不存在的数据库会报错，所以这里连接的时候不带数据库名称
            String connectionUrl = url.replace(("/" + (url.substring(0, url.indexOf("?"))).substring(((url.substring(0, url.indexOf("?"))).lastIndexOf("/")) + 1)), "");
            // 从连接地址中截取出数据库名称
            String databaseName = (url.substring(0, url.indexOf("?"))).substring(((url.substring(0, url.indexOf("?"))).lastIndexOf("/")) + 1);

            // 设置驱动
            Class.forName(driverClassName);
            // 连接数据库
            connection = DriverManager.getConnection(connectionUrl, username, password);
            statement = connection.createStatement();

            // 创建数据库
            statement.executeUpdate("create database if not exists `" + databaseName + "` default character set utf8mb4 COLLATE utf8mb4_general_ci");

        }catch (Exception e) {
            e.printStackTrace();
            log.info("==================================== InitDatabaseConfig -> dataSource -> 创建数据库出错：" + e.getMessage() + " ====================================");
        }finally {
            try {
                // 关闭连接
                statement.close();
                connection.close();
            }catch (SQLException e) {
                log.info("==================================== InitDatabaseConfig -> dataSource -> 关闭数据库出错：" + e.getMessage() + " ====================================");
            }
            log.info("==================================== InitDatabaseConfig -> dataSource -> 创建数据库结束 ====================================");
        }

        // 创建数据源
        DruidDataSource druidDataSource = new DruidDataSource();
        // 设置数据源
        druidDataSource.setDriverClassName(driverClassName);
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(password);
        druidDataSource.setInitialSize(druidDataSourceProperties.getInitialSize());
        druidDataSource.setMinIdle(druidDataSourceProperties.getMinIdle());
        druidDataSource.setMaxActive(druidDataSourceProperties.getMaxActive());
        druidDataSource.setMaxWait(druidDataSourceProperties.getMaxWait());
        druidDataSource.setTimeBetweenEvictionRunsMillis(druidDataSourceProperties.getTimeBetweenEvictionRunsMillis());
        druidDataSource.setMinEvictableIdleTimeMillis(druidDataSourceProperties.getMinEvictableIdleTimeMillis());
        druidDataSource.setValidationQuery(druidDataSourceProperties.getValidationQuery());
        druidDataSource.setTestWhileIdle(druidDataSourceProperties.isTestWhileIdle());
        druidDataSource.setTestOnBorrow(druidDataSourceProperties.isTestOnBorrow());
        druidDataSource.setTestOnReturn(druidDataSourceProperties.isTestOnReturn());
        druidDataSource.setPoolPreparedStatements(druidDataSourceProperties.isPoolPreparedStatements());
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(druidDataSourceProperties.getMaxPoolPreparedStatementPerConnectionSize());
        druidDataSource.setUseGlobalDataSourceStat(druidDataSourceProperties.isUseGlobalDataSourceStat());
        druidDataSource.setConnectProperties(druidDataSourceProperties.getConnectProperties());

        try {
            druidDataSource.setFilters(druidDataSourceProperties.getFilters());
            druidDataSource.init();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        // 返回数据源
        return druidDataSource;
    }


    @Bean("master")
    @Primary
    @DependsOn({DEFAULT_DATA_SOURCE, "springContextUtils"}) //控制加载顺序
    public DynamicDataSource dataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setTargetDataSources(DynamicDataSource.getDataSourcesMap());
        return dynamicDataSource;
    }
}