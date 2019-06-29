package com.zhuofengyuan.wechat.shop.admin.auth;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

@Data
@NoArgsConstructor
public class SecurityUser implements Serializable, UserDetails {

    private static final long serialVersionUID = 3945179659081211914L;

    private Collection<GrantedAuthority> authorities;

    private String id;

    private String nickName;

    private String loginName;

    private String loginPwd;

    private String status;

    private Long roleId;

    private String roleName;

    public SecurityUser(Collection<GrantedAuthority> authorities, String id, String nickName, String loginName, String loginPwd) {
        this.authorities = authorities;
        this.id = id;
        this.nickName = nickName;
        this.loginName = loginName;
        this.loginPwd = loginPwd;
    }

    @Override
    public String getPassword() {
        return this.getLoginPwd();
    }

    @Override
    public String getUsername() {
        return this.getLoginName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
