package com.adbt.adbtproject;

import com.adbt.adbtproject.entities.User;
import com.adbt.adbtproject.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class AdbtProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdbtProjectApplication.class, args);
    }

}
