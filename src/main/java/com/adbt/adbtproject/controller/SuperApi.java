package com.adbt.adbtproject.controller;

import com.adbt.adbtproject.entities.*;
import com.adbt.adbtproject.repo.UserRepo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.Set;

@Component
public class SuperApi implements InitializingBean {

    @Autowired
    UserRepo userRepo;

    @Override
    public void afterPropertiesSet() throws Exception {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encryptedPassword = bCryptPasswordEncoder.encode(generatedString);

        userRepo.save(new User("admin", "admin", "admin", encryptedPassword, new ContactInfo("1234", "admin@gmail.com"),
                new Address("PL", "KRK", "Warszawska", "130", "34-300"),
                Set.of(new Role(RoleOptions.ROLE_ADMIN)), Set.of()));
        System.out.println(" ADMIN GENERATED \n PASSWORD: " + generatedString + "\n EMAIL: admin@gmail.com");
    }
}
