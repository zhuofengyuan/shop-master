package com.zhuofengyuan.wechat.shop.admin.auth;

import com.zhuofengyuan.wechat.shop.admin.auth.pbi.PbiAAdTokenService;
import com.zhuofengyuan.wechat.shop.admin.auth.security.FengtoosSecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
public class PrincipalController {

    private final static String ACCESS_TOKEN = "access_token";
    @Autowired
    PbiAAdTokenService aAdTokenService;

    @GetMapping("/principal")
    public Principal getPrincipal(Principal user) {
        OAuth2Authentication auth = (OAuth2Authentication) user;
        var aadEntity = this.aAdTokenService.getRealAADToken();
        ((FengtoosSecurityUser) auth.getPrincipal()).setPbiAADToken(aadEntity.getString(ACCESS_TOKEN));
        return user;
    }
}
