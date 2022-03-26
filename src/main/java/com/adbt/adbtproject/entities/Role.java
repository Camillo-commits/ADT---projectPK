package com.adbt.adbtproject.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role implements GrantedAuthority {
    private RoleOptions role;

    @Override
    public String getAuthority() {
        return role.toString();
    }
}
