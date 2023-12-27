package com.a.chaban.uservice.userservice.models;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_EDITOR, ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}