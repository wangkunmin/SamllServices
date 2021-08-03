package com.cn.wkm.base.config;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.context.scope.refresh.RefreshScopeRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.StandardServletEnvironment;

import javax.annotation.PostConstruct;
import java.util.function.Consumer;

/**
 * @ClassName ReadConfig
 * @Description TODO
 * @Author kunmin.wang
 * @Date 2021/8/3 9:19
 * @ModifyDate 2021/8/3 9:19
 */
@RefreshScope
@Component
@Slf4j
public class ReadConfig {
    @Autowired
    private Environment env;

    @EventListener(RefreshScopeRefreshedEvent.class)
    void onRefresh(RefreshScopeRefreshedEvent event) {
        System.out.println("entering refresh");
        System.out.println("exiting refresh");
    }

    @PostConstruct
    public void init(){
        ((StandardServletEnvironment) env).getPropertySources().forEach(new Consumer<PropertySource<?>>() {
            @Override
            public void accept(PropertySource<?> propertySource) {
                if(propertySource.getName().indexOf("bootstrap.")>=0){
                    log.debug("bootstrap :[{}]",JSONUtil.formatJsonStr(propertySource.getSource().toString()));
                }
            }
        });
    }
}