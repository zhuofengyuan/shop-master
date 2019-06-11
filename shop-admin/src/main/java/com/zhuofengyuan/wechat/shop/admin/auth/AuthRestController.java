package com.zhuofengyuan.wechat.shop.admin.auth;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wechat")
public class AuthRestController {

    @GetMapping("/conn")
    public String conn(String echostr){
        System.out.println(echostr);
        return echostr;
    }
}
