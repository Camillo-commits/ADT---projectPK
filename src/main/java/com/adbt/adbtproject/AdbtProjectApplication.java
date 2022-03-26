package com.adbt.adbtproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class AdbtProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdbtProjectApplication.class, args);
    }

}
