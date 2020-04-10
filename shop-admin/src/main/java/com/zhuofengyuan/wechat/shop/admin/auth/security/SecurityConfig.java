package com.zhuofengyuan.wechat.shop.admin.auth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * Spring-Security 配置<br>
 * 具体参考: https://github.com/lexburner/oauth2-demo
 * http://blog.didispace.com/spring-security-oauth2-xjf-1/
 * https://www.cnblogs.com/cjsblog/p/9152455.html
 * https://segmentfault.com/a/1190000014371789 (多种认证方式)
 * @author wunaozai
 * @date 2018-05-28
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //启用方法级的权限认证
@Order(-1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    FengtoosUserDetailsService fengtoosUserDetailsService;
    @Autowired
    LoginAuthenticationProcider loginAuthenticationProcider;
    @Autowired
    private MyAccessDecisionManager myAccessDecisionManager;// 自定义决策管理器

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    LoginAuthenticationProcider loginAuthenticationProcider(){
        var procider = new LoginAuthenticationProcider();
        procider.setUserDetailsService(this.fengtoosUserDetailsService);
        procider.setPasswordEncoder(passwordEncoder());
        procider.setHideUserNotFoundExceptions(false);
        return procider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//            .antMatchers("/", "/index.html", "/oauth/**").permitAll() //允许访问
//            .anyRequest().authenticated() //其他地址的访问需要验证权限
//            .and()
//            .formLogin()
//            .loginPage("/login.html") //登录页
//            .failureUrl("/login-error.html").permitAll()
//            .and()
//            .logout()
//            .logoutSuccessUrl("/index.html");
        http.requestMatchers().antMatchers(HttpMethod.OPTIONS, "/oauth/**")
                .and()
                .cors()
                .and()
                .csrf().disable();

        // 将自定义的过滤器配置在FilterSecurityInterceptor之前
        http.addFilterBefore(myFilterSecurityInterceptor(), FilterSecurityInterceptor.class);
//        http.authorizeRequests().anyRequest().authenticated().and().csrf().disable();
//        http.formLogin().loginPage("/login").failureUrl("/login?code=").permitAll();
//        http.logout().permitAll();
//        http.authorizeRequests().antMatchers("/oauth/authorize").permitAll();
    }

    /**
     * 管理自定义的权限过滤器
     */
    @Bean
    public MyFilterSecurityInterceptor myFilterSecurityInterceptor() {
        MyFilterSecurityInterceptor myFilterSecurityInterceptor = new MyFilterSecurityInterceptor();
        myFilterSecurityInterceptor.setMyAccessDecisionManager(myAccessDecisionManager);
        return myFilterSecurityInterceptor;
    }

    /**
     * 用户验证
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(loginAuthenticationProcider());
        auth.userDetailsService(this.fengtoosUserDetailsService).passwordEncoder(this.passwordEncoder());
    }

    /**
     * Spring Boot 2 配置，这里要bean 注入
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        AuthenticationManager manager = super.authenticationManagerBean();
        return manager;
    }

    public static void main(String[] args) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.matches("123456", encoder.encode("123456")));
    }

}