package com.cn.wkm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

/**
 * @ClassName WebAppliction
 * @Description TODO
 * @Author kunmin.wang
 * @Date 2021/8/2 14:46
 * @ModifyDate 2021/8/2 14:46
 */
@SpringBootApplication
public class WebApplication {
    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        SpringApplication.run(WebApplication.class,args);
    }
}