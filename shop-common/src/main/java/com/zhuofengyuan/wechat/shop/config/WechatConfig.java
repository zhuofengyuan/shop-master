package com.zhuofengyuan.wechat.shop.config;

import com.zhuofengyuan.wechat.shop.prop.WechatSettings;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class WechatConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("wechat/api.yml"), new ClassPathResource("wechat/config.yml"));
        configurer.setProperties(yaml.getObject());
        return configurer;
    }

    @Bean
    @ConfigurationProperties(prefix = "wechat")
    public WechatSettings getWechatSettings(){
        return new WechatSettings();
    }
}
