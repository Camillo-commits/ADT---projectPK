package com.adbt.adbtproject.controller;

import com.adbt.adbtproject.entities.Order;
import com.adbt.adbtproject.entities.Role;
import com.adbt.adbtproject.entities.User;
import com.adbt.adbtproject.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.*;
import java.util.Map;
import java.util.Map.Entry;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.adbt.adbtproject.entities.RoleOptions.ROLE_WORKER;

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

        for (String country : countries) {
            List<User> usersInCountry = userRepo.getUsersByAddress_Country(country)
                    .stream()
                    .sorted(Comparator.comparingInt(u -> {
                        assert u.getOrders() != null;
                        return u.getOrders().size();
                    }))
                    .collect(Collectors.toList());
            usersByCountry.put(country, usersInCountry.get(isBest ? 0 : usersByCountry.size() - 1));
        }

        return usersByCountry;
    }

    @GetMapping("/topWorkersByNumberOfOrdersDelivered/{workersNumber}")
    public ResponseEntity<Map<User, Integer>> getTopWorkersByNumberOfOrdersDelivered(@PathVariable Integer workersNumber) {
        return new ResponseEntity<>(getTopWorkersByNumberOfOrders(workersNumber), HttpStatus.OK);
    }

    public Map<User, Integer> getTopWorkersByNumberOfOrders(Integer workersNumber) {

        List<User> workers = userRepo.findAll().stream().filter(worker ->
                worker.getRoleSet().contains(new Role(ROLE_WORKER))).collect(Collectors.toList());

        Map<User,Integer> deliveredOrdersMap = new HashMap<>();
        workers.stream().forEach(worker -> {
            int amountOfDeliveredOrders =
            worker.getOrders().stream().filter(order ->
                Objects.nonNull(order.getDateOfCollection())
            ).collect(Collectors.toList()).size();
            deliveredOrdersMap.put(worker, amountOfDeliveredOrders);
        });
        Map<User, Integer> sortedMap = deliveredOrdersMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1, HashMap::new))
                .entrySet()
                .stream()
                .limit(workersNumber != null ? workersNumber : 3)
                .collect(HashMap::new, (m, e) -> m.put(e.getKey(), e.getValue()), Map::putAll);
        return sortedMap;
    }


}
