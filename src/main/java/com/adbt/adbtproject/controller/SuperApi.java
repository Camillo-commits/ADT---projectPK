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
        userRepo.save(new User("id","Jan", "Kowalski", "password", new ContactInfo("1234", "jan@kowalski.pl"),
                new Address("PL", "KRK", "Warszawska", "130", "34-300"),
                Set.of(new Role(RoleOptions.ROLE_USER)), Set.of()));
        centreRepo.save( new Centre("centreId", Set.of(
                new Warehouse("name1", new Address("PL", "KRK", "Pawia", "22", "34-300"), new ContactInfo("3333", "warehouse@center.com")),
                new Warehouse("name2", new Address("PL", "Zamość", "Pawia", "22", "34-300"), new ContactInfo("666", "warehouseZ@center.com"))
        ), new Address("PL", "KRK", "Pawia", "23", "34-300")));
        return new ResponseEntity(HttpStatus.OK);
    }

}
