package com.zhuofengyuan.wechat.shop.admin.kengen;

import com.zhuofengyuan.wechat.shop.util.keygen.KeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author 【swg】.
 * @Date 2018/12/23 11:12
 * @DESC
 * @CONTACT 317758022@qq.com
 */
public class KeyGeneratorController {


    @Autowired
    @Qualifier("snowFlakeKeyGenerator")
    private KeyGenerator keyGenerator;

    @RequestMapping("/keygen")
    public String generateKey() {
        return String.valueOf(keyGenerator.generateKey());
    }

}
