package com.zhuofengyuan.wechat.shop.initialize;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhuofengyuan.wechat.shop.prop.PbiSettings;
import com.zhuofengyuan.wechat.shop.util.PbiUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class PbiInitialize implements CommandLineRunner {

    @Autowired
    PbiSettings pbiSettings;
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Override
    public void run(String... args) {
        JSONObject jsonObject = JSON.parseObject(PbiUtil.doGetAzureToken(pbiSettings));
        //设置过期时间，根据AAD Token Expires设置
        redisTemplate.opsForValue().set("fengtoos_pbi_token", jsonObject, jsonObject.getIntValue("expires_in"), TimeUnit.SECONDS);
    }
}
