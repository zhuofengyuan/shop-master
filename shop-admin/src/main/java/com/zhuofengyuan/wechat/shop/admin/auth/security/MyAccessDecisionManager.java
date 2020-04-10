package com.zhuofengyuan.wechat.shop.admin.auth.security;

import com.zhuofengyuan.wechat.shop.exception.FengtoosException;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class MyAccessDecisionManager implements AccessDecisionManager {
    /**
     * 决策方法：权限判断
     *
     * @param authentication   用户的身份信息;
     * @param object           包含客户端发起的请求的request信息，可转换为 HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
     * @param configAttributes 是MyInvocationSecurityMetadataSource的getAttributes(Object object)这个方法返回的结果，
     *                         此方法是为了判定用户请求的url 是否在权限表中，如果在权限表中，则返回给 decide 方法，用来判定用户是否有此权限;如果不在权限表中则放行。
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {

        if (configAttributes.isEmpty()) {
            return;
        }
        for (GrantedAuthority ga : authentication.getAuthorities()) {
            if (((List<ConfigAttribute>)configAttributes).get(0).getAttribute().contains(ga.getAuthority())){
                return;
            }
        }
        throw new FengtoosException("Auth Denied");//AccessDeniedException
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}