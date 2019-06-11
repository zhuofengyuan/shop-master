package com.zhuofengyuan.wechat.shop.common;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class ShopCommonApplicationTests {

//  @Test
  public void contextLoads() {}

  public static void main(String[] args) {
    System.out.println(new BigDecimal(-154).signum());
    System.out.println(new BigDecimal(0).signum());
    System.out.println(new BigDecimal(11213).signum());
  }
}
