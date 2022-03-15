package com.adbt.adbtproject;

import com.adbt.adbtproject.entities.Centre;
import com.adbt.adbtproject.repo.CentreRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

@SpringBootApplication
public class AdbtProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdbtProjectApplication.class, args);
    }

}
