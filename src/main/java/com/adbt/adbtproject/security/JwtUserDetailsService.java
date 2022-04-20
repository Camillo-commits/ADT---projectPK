package com.adbt.adbtproject.security;

import com.adbt.adbtproject.entities.User;
import com.adbt.adbtproject.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String number) throws UsernameNotFoundException {

        User user = userRepo.getUserByContactInfo_TelNumber(number);
        if (user != null) {
            return new org.springframework.security.core.userdetails.User(user.getContactInfo().getTelNumber(), user.getPassword(), user.getRoleSet());
        }
        return null;
    }
}
