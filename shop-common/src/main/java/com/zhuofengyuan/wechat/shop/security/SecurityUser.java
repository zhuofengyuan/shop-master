package com.zhuofengyuan.wechat.shop.security;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class SecurityUser implements Serializable {

    private static final long serialVersionUID = 3945179659081211914L;

    public SecurityUser(String id, String nickName, String loginName, String loginPwd){
        this.id = id;
        this.nickName = nickName;
        this.loginName = loginName;
        this.loginPwd = loginPwd;
    }

    private String id;

    private String nickName;

    private String loginName;

    private String loginPwd;

    private String status;

    private Long roleId;

    private String roleName;
}
