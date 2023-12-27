package com.a.chaban.sts.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_EDITOR, ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}