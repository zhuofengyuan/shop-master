package com.zhuofengyuan.wechat.shop.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.zhuofengyuan.wechat"})
public class ShopAdminApplication {

  public static void main(String[] args) {
    SpringApplication.run(ShopAdminApplication.class, args);
  }
}
