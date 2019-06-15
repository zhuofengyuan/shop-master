package com.zhuofengyuan.wechat.shopweb.config;

import com.zhuofengyuan.wechat.shop.util.keygen.KeyGenerator;
import com.zhuofengyuan.wechat.shop.util.keygen.UUIDKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * id生成器
 * @author fengtoos
 */
@Configuration
public class KeyGenConfig {

    @Bean
    public KeyGenerator<String> uuidGenerator(){
        return new UUIDKeyGenerator();
    }
}
