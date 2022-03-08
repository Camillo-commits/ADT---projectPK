package com.adbt.adbtproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

@SpringBootApplication(scanBasePackageClasses = org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory.class)/*(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})*/
public class AdbtProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdbtProjectApplication.class, args);
    }

}
