package com.zhuofengyuan.wechat.shop.prop;

import lombok.Data;

@Data
public class WechatSettings {
    /** 微信工作号秘钥 */
    private String appid;
    private String appsecret;

    /** 微信官网API地址 */
    private String login;
    private String token;

    /** 微信授权码 */
    private String accessTokenKey;
    private String refreshTokenKey;
}
