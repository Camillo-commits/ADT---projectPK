package com.adbt.adbtproject.controller;

import com.adbt.adbtproject.entities.ContactInfo;
import com.adbt.adbtproject.entities.ItemGroup;
import com.adbt.adbtproject.entities.Order;
import com.adbt.adbtproject.entities.User;
import com.adbt.adbtproject.repo.ItemGroupRepo;
import com.adbt.adbtproject.repo.OrderRepo;
import com.adbt.adbtproject.repo.UserRepo;
import com.adbt.adbtproject.requestUtils.OrderAddRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicBoolean;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private ItemGroupRepo itemGroupRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private UserRepo userRepo;

    @PostMapping
    public ResponseEntity<String> placeOrder(@RequestBody OrderAddRequest request){
        Order order = request.getOrder();
        ContactInfo userDetails = request.getUserDetails();
        AtomicBoolean allItemsAvailable = new AtomicBoolean(true);
        order.getItemGroups().forEach(item -> {
            ItemGroup itemGroup = itemGroupRepo.getItemGroupByName(item.getName());
            if(itemGroup.allAvailability() == 0) {
                allItemsAvailable.set(false);
            }
        });
        if(allItemsAvailable.get()) {
            order = orderRepo.save(order);
            User user = userRepo.getUserByContactInfo_Email(userDetails.getEmail());
            user.addOrder(order);
            userRepo.save(user);
        } else {
            return new ResponseEntity<String>("Item not available", HttpStatus.CONFLICT);
        }
        return new ResponseEntity("Order placed: " + order.toString(), HttpStatus.CREATED);
    }

}
