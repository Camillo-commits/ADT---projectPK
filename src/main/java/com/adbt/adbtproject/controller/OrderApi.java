package com.adbt.adbtproject.controller;

import com.adbt.adbtproject.entities.ContactInfo;
import com.adbt.adbtproject.entities.ItemGroup;
import com.adbt.adbtproject.entities.Order;
import com.adbt.adbtproject.entities.User;
import com.adbt.adbtproject.repo.ItemGroupRepo;
import com.adbt.adbtproject.repo.UserRepo;
import com.adbt.adbtproject.requestUtils.OrderAddRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderApi {

    @Autowired
    private ItemGroupRepo itemGroupRepo;

    @Autowired
    private UserRepo userRepo;

    @PostMapping
    public ResponseEntity<Object> placeOrder(@RequestBody OrderAddRequest request) {
        Order order = request.getOrder();
        ContactInfo userDetails = request.getUserDetails();
        AtomicBoolean allItemsAvailable = new AtomicBoolean(true);
        Set<ItemGroup> items = new HashSet<>();
        order.getItemGroups().forEach(item -> {
            ItemGroup itemGroup = itemGroupRepo.getItemGroupByName(item.getName());
            if (itemGroup.allAvailability() == 0) {
                allItemsAvailable.set(false);
            }
            items.add(itemGroup);
        });
        items.forEach(item -> item.setId(null));
        order.setItemGroups(items);
        Date date = new Date();
        order.setDateOfOrder(date);
        order.setTodo(true);

        if (allItemsAvailable.get()) {
            User user = userRepo.getUserByContactInfo_Email(userDetails.getEmail());
            order.setId(generateId(user, order, date));
            user.addOrder(order);
            userRepo.save(user);
        } else {
            return new ResponseEntity<>("Item not available", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>("Order placed: " + order, HttpStatus.CREATED);
    }

    @GetMapping("/todo")
    public ResponseEntity<Object> getUnfinishedOrders() {
        List<User> waitingUsers = userRepo.getUsersByOrders_TodoIsTrue();
        List<Order> awaitingOrders = waitingUsers.stream()
                .flatMap(user -> user.getOrders()
                        .stream()
                        .filter(Order::isTodo))
                .collect(Collectors.toList());
        return new ResponseEntity<>(awaitingOrders, HttpStatus.OK);
    }

    private String generateId(User user, Order order, Date date) {
        return String.format("%s-%s-%s", user.getContactInfo().getEmail(), order.getItemGroups().size(), date.getTime());
    }
}
