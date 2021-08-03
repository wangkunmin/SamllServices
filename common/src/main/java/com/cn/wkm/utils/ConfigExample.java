package com.cn.wkm.utils;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;

import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * @ClassName ConfigExample
 * @Description TODO
 * @Author kunmin.wang
 * @Date 2021/8/3 10:03
 * @ModifyDate 2021/8/3 10:03
 */
public class ConfigExample {
    public static void main(String[] args) throws NacosException, InterruptedException {
        String serverAddr = "192.168.27.152:8848";
        String dataId = "nacos-config.YAML";
        String group = "DEFAULT_GROUP";
        String namespace = "d2443b64-30a3-4b08-bc84-6b95ece1dad6";
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.NAMESPACE, namespace);
        properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);
        ConfigService configService = NacosFactory.createConfigService(properties);
        String content = configService.getConfig(dataId, group, 5000);
        System.out.println(content);
        configService.addListener(dataId, group, new Listener() {
            @Override
            public void receiveConfigInfo(String configInfo) {
                System.out.println("recieve:" + configInfo);
            }

            @Override
            public Executor getExecutor() {
                return null;
            }
        });

//        boolean isPublishOk = configService.publishConfig(dataId, group, "content");
        //更改配置
//        System.out.println(isPublishOk);
//
//        Thread.sleep(3000);
//        content = configService.getConfig(dataId, group, 5000);
//        System.out.println(content);
            //刪除配置
//        boolean isRemoveOk = configService.removeConfig(dataId, group);
////        System.out.println(isRemoveOk);
//        Thread.sleep(3000);

        content = configService.getConfig(dataId, group, 5000);
        System.out.println(content);
        Thread.sleep(3000);

    }
}