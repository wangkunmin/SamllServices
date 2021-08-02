package com.cn.wkm.utils;


import lombok.Getter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * @ClassName SpringContextUtils
 * @Description TODO
 * @Author kunmin.wang
 * @Date 2021/7/30 17:13
 * @ModifyDate 2021/7/30 17:13
 */
@Component
public class SpringContextUtils implements ApplicationContextAware {
    @Getter
    private static ApplicationContext applicationContext;

    @NonNull
    public static <T> T getBean(Class<T> clazz) {
        return SpringContextUtils.applicationContext.getBean(clazz);
    }

    @NonNull
    public static <T> T getBean(String name) {
        //noinspection unchecked
        return (T) SpringContextUtils.applicationContext.getBean(name);
    }

    public static String getProperty(String key) {
        return SpringContextUtils.applicationContext.getEnvironment().getProperty(key);
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringContextUtils.applicationContext == null) {
            SpringContextUtils.applicationContext = applicationContext;
        }
    }
}