package com.zhuofengyuan.wechat.shop.admin.auth.security;

import com.zhuofengyuan.wechat.shop.security.SecurityUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class FengtoosSecurityUser extends SecurityUser implements Serializable, UserDetails {

    private static final long serialVersionUID = 3945179659081211914L;

    /** 权限列表 */
    private Collection<GrantedAuthority> authorities;
    /** pbi的AADToken */
    private String pbiAADToken;

    public FengtoosSecurityUser(Collection<GrantedAuthority> authorities, String id, String nickName, String loginName, String loginPwd, String logo, String aadToken) {
        super(id, nickName, loginName, loginPwd, logo);
        this.authorities = authorities;
        this.pbiAADToken = aadToken;
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
