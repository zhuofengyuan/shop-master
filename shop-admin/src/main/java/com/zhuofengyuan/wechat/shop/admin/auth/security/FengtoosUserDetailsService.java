package com.zhuofengyuan.wechat.shop.admin.auth.security;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhuofengyuan.wechat.shop.admin.auth.pbi.PbiAAdTokenService;
import com.zhuofengyuan.wechat.shop.entity.Authorization;
import com.zhuofengyuan.wechat.shop.entity.User;
import com.zhuofengyuan.wechat.shop.prop.PbiSettings;
import com.zhuofengyuan.wechat.shop.service.IAuthorizationService;
import com.zhuofengyuan.wechat.shop.service.IUserService;
import com.zhuofengyuan.wechat.shop.util.PbiUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Order(1)
@Component
public class FengtoosUserDetailsService implements UserDetailsService {

    @Autowired
    IUserService userService;
    @Autowired
    IAuthorizationService authorizationService;
    @Autowired
    PbiAAdTokenService aAdTokenService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User entity = this.userService.findByUsername(username);
        if(entity == null){
            throw new UsernameNotFoundException("用户名认证失败");
        }
        return this.createSecurityUser(entity);
    }

    private FengtoosSecurityUser createSecurityUser(User entity) {
        String id = entity.getId();
        List<Authorization> auths = this.authorizationService.selectByUserId(id);

        var aadEntityObj = this.aAdTokenService.getRealAADToken();

        List<GrantedAuthority> authorizations = auths.stream().filter(a -> StringUtils.isNotEmpty(a.getCode()))
                .map(a -> new FengtoosGrantedAuthority(a.getCode(), a.getPath(), a.getId())).collect(Collectors.toList());
        return new FengtoosSecurityUser(
                authorizations,
                entity.getId(),
                entity.getScreenName(),
                entity.getUsername(),
                entity.getPassword(),
                entity.getLogo(),
                aadEntityObj.getString("access_token")
        );
    }
}
