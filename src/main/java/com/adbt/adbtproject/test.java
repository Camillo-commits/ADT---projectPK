package com.adbt.adbtproject;

import com.adbt.adbtproject.entities.Centre;
import com.adbt.adbtproject.repo.CentreRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

@RequiredArgsConstructor
public class test implements ApplicationListener {

    @Autowired
    private CentreRepo centreRepo;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {

        Centre centre = new Centre();
        centreRepo.save(centre);
    }
}
