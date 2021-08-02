package com.cn.wkm.base.config.druid;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
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
public class DruidConfiguration {
    /**
     * 默认数据源
     */
    public final static String DEFAULT_DATA_SOURCE = "defaultDataSource";

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
    @SuppressWarnings("ConfigurationProperties")
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.druid")
    public DataSource defaultDataSource() {
        return new DruidDataSource();
    }

    @Bean
    @Primary
    @DependsOn({DEFAULT_DATA_SOURCE, "springContextUtils"})
    public DynamicDataSource dataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setTargetDataSources(DynamicDataSource.getDataSourcesMap());
        return dynamicDataSource;
    }
}