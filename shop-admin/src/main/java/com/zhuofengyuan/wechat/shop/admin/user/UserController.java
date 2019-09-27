package com.zhuofengyuan.wechat.shop.admin.user;

import com.zhuofengyuan.wechat.shop.entity.User;
import com.zhuofengyuan.wechat.shop.prop.WechatSettings;
import com.zhuofengyuan.wechat.shop.resp.RestResponseBo;
import com.zhuofengyuan.wechat.shop.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserService userService;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    WechatSettings wechatSettings;

    /**
     * 获取系统用户信息
     * @param openid
     * @param avatarUrl
     * @param nickname
     * @return
     */
    @PostMapping("/wx/{id}")
    public RestResponseBo getWxUserByOpenId(@PathVariable("id") String openid, String avatarUrl, String nickname){
        var user = new User();
        user.setOpenid(openid);
        user.setLogo(avatarUrl);
        user.setName(nickname);
        user.setScreenName(nickname);
        user.setPassword(encoder.encode(wechatSettings.getInitialPwd()));
        return RestResponseBo.ok(this.userService.findWxUser(user));
    }
}
