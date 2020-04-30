package com.zhuofengyuan.wechat.shop.admin.auth.security;

import com.zhuofengyuan.wechat.shop.prop.IgnoreUrlSettings;
import com.zhuofengyuan.wechat.shop.service.IAuthorizationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class MyFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private IAuthorizationService authorizationService;
    @Autowired
    private IgnoreUrlSettings ignoreUrlSettings;

    /**
     * 加载资源，初始化资源变量
     */
    public Collection<ConfigAttribute> getAuthByUrl(String url) {
        Object r = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(r instanceof FengtoosSecurityUser){
            var auths = this.authorizationService.selectByUserId(((FengtoosSecurityUser) r).getId())
                    .stream()
                    .filter(auth -> StringUtils.isNotEmpty(auth.getAuthurl()))
                    .filter(auth -> url.contains(auth.getAuthurl()))
                    .map(auth -> (ConfigAttribute) () -> auth.getCode())
                    .collect(Collectors.toList());
            if(!auths.isEmpty()){
                return auths;
            }
        }
        return Arrays.asList((ConfigAttribute) () -> "EMPTY_AUTH");
//        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        FilterInvocation fi = (FilterInvocation) object;
        if("OPTIONS".equals(fi.getHttpRequest().getMethod())){
            return null;
        }
        String url = fi.getRequestUrl();
        if("/".equals(url)){
            return null;
        }

        //过滤自定义不需要验证的URL
        var urls = this.ignoreUrlSettings.getUrls();
        for(String item : urls){
            if(url.contains(item)){
                return null;
            }
        }
        return this.getAuthByUrl(url);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}