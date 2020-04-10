package com.zhuofengyuan.wechat.shop.admin.auth.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * OAuth 资源服务器配置
 * @author fengtoos
 * @date 2018-05-29
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private static final String DEMO_RESOURCE_ID = "order";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(DEMO_RESOURCE_ID).stateless(true);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // Since we want the protected resources to be accessible in the UI as well we need
        // session creation to be allowed (it's disabled by default in 2.0.6)
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .requestMatchers().anyRequest()
                .and()
                .anonymous()
                .and()
//            .authorizeRequests()
//            .antMatchers("/order/**").authenticated();//配置order访问控制，必须认证过后才可以访问
                .authorizeRequests()
//                .antMatchers("/order/**").hasAuthority("admin_role")
                .antMatchers("/admin/product/**", "/wx/api/**", "/admin/file/**", "/user/wx/**").permitAll();
//                .anyRequest().authenticated();
//            .antMatchers("/order/**").hasAnyRole("admin");
    }

}
