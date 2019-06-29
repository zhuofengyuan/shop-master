package com.zhuofengyuan.wechat.shop.common;

import java.math.BigDecimal;

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
