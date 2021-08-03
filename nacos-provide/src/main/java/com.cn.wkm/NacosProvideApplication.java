package com.cn.wkm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @ClassName NacosProvideApplication
 * @Description TODO
 * @Author kunmin.wang
 * @Date 2021/7/30 16:10
 * @ModifyDate 2021/7/30 16:10
 */
@EnableDiscoveryClient
@SpringBootApplication
public class NacosProvideApplication {
    public static void main(String[] args) {
        SpringApplication.run(NacosProvideApplication.class, args);
    }
}