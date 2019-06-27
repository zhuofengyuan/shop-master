package com.zhuofengyuan.wechat.shop.admin.auth;

import com.zhuofengyuan.wechat.shop.admin.settings.WechatSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wechat")
public class AuthRestController {

    @Autowired
    WechatSettings wechatSettings;

    @GetMapping
    public String conn(String echostr){
        System.out.println(echostr);
        return echostr;
    }

    @GetMapping("/test")
    public String test(){
        return "hello world";
    }

    @GetMapping("/prop")
    public WechatSettings getWechatSettings(){
        return this.wechatSettings;
    }
}
