package com.zhuofengyuan.wechat.shop.admin.auth;

import com.zhuofengyuan.wechat.shop.entity.Authorization;
import com.zhuofengyuan.wechat.shop.entity.User;
import com.zhuofengyuan.wechat.shop.service.IAuthorizationService;
import com.zhuofengyuan.wechat.shop.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FengtoosUserDetailsService implements UserDetailsService {

    @Autowired
    IUserService userService;
    @Autowired
    IAuthorizationService authorizationService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User entity = this.userService.findByUsername(username);
        if(entity == null){
            throw new UsernameNotFoundException("用户名认证失败");
        }
        return this.createSecurityUser(entity);
    }

    private SecurityUser createSecurityUser(User entity){
        String id = entity.getId();
        List<Authorization> auths = this.authorizationService.selectByUserId(id);

        List<GrantedAuthority> authorizations = auths.stream().filter(a -> StringUtils.isNotEmpty(a.getCode()))
                .map(a -> new SimpleGrantedAuthority(a.getCode())).collect(Collectors.toList());
        return new SecurityUser(authorizations, entity.getId(), entity.getScreenName(), entity.getUsername(), entity.getPassword());
    }
}
