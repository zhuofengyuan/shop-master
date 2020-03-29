package com.zhuofengyuan.wechat.shop.admin.auth;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhuofengyuan.wechat.shop.admin.auth.pbi.PbiAAdTokenService;
import com.zhuofengyuan.wechat.shop.admin.auth.security.FengtoosSecurityUser;
import com.zhuofengyuan.wechat.shop.prop.PbiSettings;
import com.zhuofengyuan.wechat.shop.prop.WechatSettings;
import com.zhuofengyuan.wechat.shop.service.IProvinceTaskService;
import com.zhuofengyuan.wechat.shop.util.ExcelTest;
import com.zhuofengyuan.wechat.shop.util.PbiUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/wechat")
public class AuthRestController {

    @Autowired
    WechatSettings wechatSettings;
    @Autowired
    PbiAAdTokenService aAdTokenService;
    @Autowired
    IProvinceTaskService provinceTaskService;

    @GetMapping
    public String conn(String echostr){
        System.out.println(echostr);
        return echostr;
    }

    @GetMapping("/test")
    public String test() throws Exception {
//        provinceTaskService.saveBatch(ExcelTest.readTable("F:\\fengtoos\\powerbi\\2020\\template.xlsx"));
        return "hello world";
    }

    @GetMapping("/principal")
    public Principal getPrincipal(Principal user) {
        OAuth2Authentication auth = (OAuth2Authentication) user;
        var aadEntity = this.aAdTokenService.getRealAADToken();
        ((FengtoosSecurityUser) auth.getPrincipal()).setPbiAADToken(aadEntity.getString("access_token"));
        return user;
    }
}
