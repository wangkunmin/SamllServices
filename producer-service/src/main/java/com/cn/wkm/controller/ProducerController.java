package com.cn.wkm.controller;

import com.cn.wkm.entity.Producer;
import com.cn.wkm.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProducerController {
    @Autowired
    private RedisTemplate redisTemplate;


    @PostMapping("/put/producer")
    public Result putKV(@RequestBody Producer producer){
        redisTemplate.opsForValue().set(producer.getName(),producer.getText());
       return new Result(200,"添加成功");
    }

    @GetMapping("/get/{key}")
    public Result getKV(@PathVariable(value = "key")String key){
        return new Result(200,"读取成功",redisTemplate.opsForValue().get(key));
    }

}
