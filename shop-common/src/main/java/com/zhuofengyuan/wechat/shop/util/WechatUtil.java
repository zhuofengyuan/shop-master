package com.zhuofengyuan.wechat.shop.util;

import com.alibaba.druid.util.StringUtils;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WechatUtil {

  /**
   * 判断是否加密
   *
   * @param token
   * @param signature
   * @param timestamp
   * @param nonce
   * @return
   */
  public static boolean checkSignature(String token, String signature, String timestamp, String nonce) {
    log.info(
        "###token:"
            + token
            + ";signature:"
            + signature
            + ";timestamp:"
            + timestamp
            + "nonce:"
            + nonce);
    boolean flag = false;
    if (!StringUtils.isEmpty(signature) && !StringUtils.isEmpty(timestamp) && !StringUtils.isEmpty(nonce)) {
      String sha1 = signature(signature, timestamp, nonce);
      if (sha1.equals(signature)) {
        flag = true;
      }
    }
    return flag;
  }

  public static String signature(String signature, String timestamp, String nonce) {
    String sha1 = "";
    String[] ss = new String[] {signature, timestamp, nonce};
    Arrays.sort(ss);
    for (String s : ss) {
      sha1 += s;
    }

    return ShaUtil.SHA1(sha1);
  }
}
