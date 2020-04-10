package com.zhuofengyuan.wechat.shop.admin.auth.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

public class FengtoosGrantedAuthority implements GrantedAuthority {

    private final String role;
    private String path;
    private String id;

    public FengtoosGrantedAuthority(String role, String path, String id) {
        Assert.hasText(role, "A granted authority textual representation is required");
        this.role = role;
        this.path = path;
        this.id = id;
    }

    @Override
    public String getAuthority() {
        return this.role;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else {
            return obj instanceof FengtoosGrantedAuthority ? this.role.equals(((FengtoosGrantedAuthority)obj).role) : false;
        }
    }

    public int hashCode() {
        return this.role.hashCode();
    }

    public String toString() {
        return this.role;
    }
}
