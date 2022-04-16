package com.adbt.adbtproject.controller;

import com.adbt.adbtproject.entities.Role;
import com.adbt.adbtproject.entities.User;
import com.adbt.adbtproject.repo.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import static com.adbt.adbtproject.entities.RoleOptions.ROLE_WORKER;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/api/analytics", produces = APPLICATION_JSON_VALUE)
public class AnalyticsApi {

    public static final String DB_URI = "mongodb://localhost:27017/warehouse";

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
    public ResponseEntity<Object> getBestUserInCountryByOrderNumber() {
        MongoClient mongoClient = MongoClients.create(DB_URI);
        MongoDatabase database = mongoClient.getDatabase("warehouse");
        MongoCollection<Document> collection = database.getCollection("users");
        List<String> result = new ArrayList<>();
        collection.aggregate(Arrays.asList(
                Aggregates.match(Filters.exists("orders")),
                Aggregates.unwind("$orders"),
                Aggregates.group("$contactInfo", Accumulators.sum("totalSum", "$orders.totalPrice"), Accumulators.first("nationality", "$address.country")),
                Aggregates.sort(Sorts.ascending("totalSum")),
                Aggregates.group("$nationality", Accumulators.max("maxSum", "$totalSum"), Accumulators.last("user", "$_id")),

                Aggregates.out("result")

        )).forEach(document -> result.add(document.toJson()));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/worstCustomerByNumberInCountry")
    public ResponseEntity<Object> getWorstUserInCountryByOrderNumber() {
        MongoClient mongoClient = MongoClients.create(DB_URI);
        MongoDatabase database = mongoClient.getDatabase("warehouse");
        MongoCollection<Document> collection = database.getCollection("users");
        List<String> result = new ArrayList<>();
        collection.aggregate(Arrays.asList(
                Aggregates.match(Filters.exists("orders")),
                Aggregates.unwind("$orders"),
                Aggregates.group("$contactInfo", Accumulators.sum("totalSum", "$orders.totalPrice"), Accumulators.first("nationality", "$address.country")),
                Aggregates.sort(Sorts.ascending("totalSum")),
                Aggregates.group("$nationality", Accumulators.min("maxSum", "$totalSum"), Accumulators.first("user", "$_id")),

                Aggregates.out("result")

        )).forEach(document -> result.add(document.toJson()));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/topWorkersByNumberOfOrdersDelivered/{workersNumber}")
    public ResponseEntity<Map<User, Integer>> getTopWorkersByNumberOfOrdersDelivered(@PathVariable Integer workersNumber) {
        return new ResponseEntity<>(getTopWorkersByNumberOfOrders(workersNumber), HttpStatus.OK);
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

    public Map<User, Integer> getTopWorkersByNumberOfOrders(Integer workersNumber) {

        List<User> workers = userRepo.findAll().stream().filter(worker ->
                worker.getRoleSet().contains(new Role(ROLE_WORKER))).collect(Collectors.toList());

        Map<User, Integer> deliveredOrdersMap = new HashMap<>();
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
