package com.adbt.adbtproject.security;

import com.adbt.adbtproject.entities.Role;
import com.adbt.adbtproject.entities.RoleOptions;
import com.adbt.adbtproject.entities.User;
import com.adbt.adbtproject.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if(email.equals("admin")){
            return new org.springframework.security.core.userdetails.User("admin", "admin", Arrays.asList(new Role(RoleOptions.ROLE_ADMIN)));
        }
        User user = userRepo.getUserByContactInfo_Email(email);
        if(user != null) {
            return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(),Arrays.asList(new Role(RoleOptions.ROLE_ADMIN)) /* user.getRoleSet().stream().map(Role::new).collect(Collectors.toList())*/);
        }
        return null;
    }
}
