package com.adbt.adbtproject.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
@AllArgsConstructor
public class Role implements GrantedAuthority {
    private RoleOptions role;

    @Override
    public String getAuthority() {
        return role.toString();
    }
}
