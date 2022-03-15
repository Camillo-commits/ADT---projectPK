package com.adbt.adbtproject.controller;

import com.adbt.adbtproject.entities.*;
import com.adbt.adbtproject.repo.CentreRepo;
import com.adbt.adbtproject.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/internal/initDatabase")
public class SuperApi {

    @Autowired
    CentreRepo centreRepo;
    @Autowired
    UserRepo userRepo;

    @GetMapping
    public ResponseEntity<HttpStatus> initDatabase() {
        userRepo.save(new User("Jan", "Kowalski", new ContactInfo("1234", "jan@kowalski.pl"), new Address("PL", "KRK", "Warszawska", "130", "34-300"), Set.of(Role.ROLE_USER)));
        centreRepo.save(new Centre(List.of(
                new Warehouse(new Address("PL", "KRK", "Pawia", "22", "34-300"), new ContactInfo("3333", "warehouse@center.com")),
                new Warehouse(new Address("PL", "Zamość", "Pawia", "22", "34-300"), new ContactInfo("666", "warehouseZ@center.com"))
        )));
        return new ResponseEntity(HttpStatus.OK);
    }

}
