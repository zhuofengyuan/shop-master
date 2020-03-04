package com.zhuofengyuan.wechat.shop.admin.auth.pbi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhuofengyuan.wechat.shop.prop.PbiSettings;
import com.zhuofengyuan.wechat.shop.util.PbiUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class PbiAAdTokenService {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    @Autowired
    PbiSettings pbiSettings;

    public JSONObject getRealAADToken(){
        /** PBI AAD Token From Redis*/
        var aadEntity = this.redisTemplate.opsForValue().get("fengtoos_pbi_token");
        if(aadEntity == null){
            aadEntity = PbiUtil.doGetAzureToken(pbiSettings);

            // 重新设置AAD Token
            var obj = JSON.parseObject(aadEntity.toString());
            this.redisTemplate.opsForValue().set("fengtoos_pbi_token", aadEntity.toString(), obj.getIntValue("expires_in"), TimeUnit.SECONDS);
        }
        return JSON.parseObject(aadEntity.toString());
    }
}
