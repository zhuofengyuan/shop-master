package com.zhuofengyuan.wechat.shop.prop;

import lombok.Data;

@Data
public class PbiSettings {
    private String resource;
    /** Azure 的应用ID*/
    private String client_id;
    /** Azure 的应用密码*/
    private String client_secret;
    private String grant_type;
    /** Pbi Pro账号*/
    private String username;
    /** Pbi Pro密码*/
    private String password;

    /** AAD的Token访问URL*/
    private String AADToken;
}
