package com.zhuofengyuan.wechat.shop.admin.user;
import	java.time.LocalDateTime;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhuofengyuan.wechat.shop.entity.User;
import com.zhuofengyuan.wechat.shop.prop.WechatSettings;
import com.zhuofengyuan.wechat.shop.resp.RestResponseBo;
import com.zhuofengyuan.wechat.shop.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserService userService;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    WechatSettings wechatSettings;
    @Value("${fengtoos.default.avaterUrl}")
    String defalutAvaterUrl;

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
        user.setStatus(1);
        user.setPassword(encoder.encode(wechatSettings.getInitialPwd()));
        return RestResponseBo.ok(this.userService.findWxUser(user));
    }

    @GetMapping("/list")
    public RestResponseBo list(@RequestParam(name = "page", defaultValue = "0") Integer pageNumber,
                               @RequestParam(name = "limit", defaultValue = "10") Integer pageSize){
        Page<User> page = new Page<>(pageNumber, pageSize);
        return RestResponseBo.ok(this.userService.page(page), 0);
    }

    @PostMapping("/add")
    public RestResponseBo add(@RequestBody User entity){
        entity.setStatus(1);
        entity.setCreateDate(LocalDateTime.now());
        entity.setScreenName(entity.getName());
        entity.setLogo(StringUtils.isEmpty(entity.getLogo())?defalutAvaterUrl:entity.getLogo());
        entity.setPassword(encoder.encode(entity.getPassword()));
        return RestResponseBo.normal(this.userService.save(entity));
    }

    @PostMapping("/update")
    public RestResponseBo update(@RequestBody User entity){
        return RestResponseBo.normal(this.userService.updateById(entity));
    }

    @DeleteMapping("/{id}")
    public RestResponseBo delete(@PathVariable String id){
        return RestResponseBo.normal(this.userService.removeById(id));
    }

    @PostMapping("/action/{id}")
    public RestResponseBo disable(@PathVariable String id, Integer status){
        return RestResponseBo.normal(this.userService.action(id, status));
    }
}
