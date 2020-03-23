package com.zhuofengyuan.wechat.shop.admin.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登出操作
 */
@FrameworkEndpoint
public class RevokeTokenEndpoint {

    @Autowired
    @Qualifier("consumerTokenServices")
    ConsumerTokenServices consumerTokenServices;
    @Value("${spring.profiles.active}")
    private String active;

    @RequestMapping(method = RequestMethod.DELETE, value = "/oauth/token")
    @ResponseBody
    public boolean revokeToken(String access_token) {
        if (consumerTokenServices.revokeToken(access_token)) {
            return true;
        } else {
            return false;
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/")
    @ResponseBody
    public void index(HttpServletResponse response) throws ServletException, IOException {
        if("prod".equalsIgnoreCase(active)){
            response.sendRedirect("http://zm.zsmls.com/index.html");
        }
    }
}