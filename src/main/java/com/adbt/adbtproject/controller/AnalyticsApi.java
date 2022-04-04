package com.adbt.adbtproject.controller;

import com.adbt.adbtproject.entities.User;
import com.adbt.adbtproject.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsApi {

    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ItemGroupRepo itemGroupRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private WarehouseRepo warehouseRepo;
    @Autowired
    private CentreRepo centreRepo;

    @GetMapping("/topCustomerByNumberInCountry")
    public ResponseEntity<Map<String, User>> getBestUserInCountryByOrderNumber() {
        return new ResponseEntity<>(getUserByCountry(true), HttpStatus.OK);
    }

    @GetMapping("/worstCustomerByNumberInCountry")
    public ResponseEntity<Map<String, User>> getWorstUserInCountryByOrderNumber() {
        return new ResponseEntity<>(getUserByCountry(false), HttpStatus.OK);
    }

    private Map<String, User> getUserByCountry(boolean isBest) {
        List<User> users = userRepo.findAll();
        Set<String> countries = users.stream().map(u -> u.getAddress().getCountry()).collect(Collectors.toSet());

        Map<String, User> usersByCountry = new HashMap<>();

        for(String country: countries) {
            List<User> usersInCountry = userRepo.getUsersByAddress_Country(country)
                    .stream()
                    .sorted(Comparator.comparingInt(u -> {
                        assert u.getOrders() != null;
                        return u.getOrders().size();
                    }))
                    .collect(Collectors.toList());
            usersByCountry.put(country, usersInCountry.get(isBest?0: usersByCountry.size()-1));
        }

        return usersByCountry;
    }

}
