package com.adbt.adbtproject.controller;

import com.adbt.adbtproject.entities.*;
import com.adbt.adbtproject.repo.ItemGroupRepo;
import com.adbt.adbtproject.repo.UserRepo;
import com.adbt.adbtproject.requestUtils.OrderAddRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.adbt.adbtproject.entities.RoleOptions.ROLE_WORKER;

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
        List<NotUniqueItemGroup> items = new ArrayList<>();
        order.getItemGroups().forEach(item -> {
            ItemGroup itemGroup = itemGroupRepo.getItemGroupByName(item.getName());
            if (itemGroup.allAvailability() == 0) {
                allItemsAvailable.set(false);
            }
            items.add(new NotUniqueItemGroup(itemGroup));
        });
        items.forEach(item -> item.setId(null));
        order.setItemGroups(items);
        order.setItems(new ArrayList<>(items.size()));
        order.setTotalPrice(items.stream().map(NotUniqueItemGroup::getPrice).mapToDouble(Double::doubleValue).sum());
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
        return new ResponseEntity<>(order, HttpStatus.CREATED);
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

    @PatchMapping("/retrieved/{orderId}")
    public ResponseEntity<Object> retrieveOrder(@PathVariable String orderId) {
        User user = userRepo.getUserByOrders_Id(orderId);
        if (user == null) {
            return new ResponseEntity<>("Order not found", HttpStatus.BAD_REQUEST);
        }
        /*if (user.getRoleSet() == null) {
            return new ResponseEntity<>("User does not have permission to finish order", HttpStatus.UNAUTHORIZED);
        }*/
       /* if (!user.getRoleSet().contains(new Role(ROLE_SHIFT_MANAGER)) || !user.getRoleSet().contains(new Role(ROLE_WORKER))) {
            return new ResponseEntity<>("User does not have permission to retrieve order", HttpStatus.UNAUTHORIZED);
        }*/
        if (user.getOrders() == null) {
            return new ResponseEntity<>("Order not found", HttpStatus.BAD_REQUEST);
        }
        Optional<Order> orderOpt = user.getOrders().stream()
                .filter(ord -> ord.getId().equals(orderId)).findAny();
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            order.setDateOfRetrieval(new Date());
            userRepo.save(user);
            return new ResponseEntity<>("Order retrieved", HttpStatus.OK);
        }
        return new ResponseEntity<>("Order not found", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/{orderId}/items")
    public ResponseEntity<Object> addItemToTheOrder(@PathVariable String orderId, @RequestBody List<Item> items) {
        User user = userRepo.getUserByOrders_Id(orderId);
        if (user == null) {
            return new ResponseEntity<>("Order not found", HttpStatus.BAD_REQUEST);
        }
        /*if (user.getRoleSet() == null) {
            return new ResponseEntity<>("User does not have permission to finish order", HttpStatus.UNAUTHORIZED);
        }*/
       /* if (!user.getRoleSet().contains(new Role(ROLE_SHIFT_MANAGER))) {
            return new ResponseEntity<>("User does not have permission to finish order", HttpStatus.UNAUTHORIZED);
        }*/
        if (user.getOrders() == null) {
            return new ResponseEntity<>("Order not found", HttpStatus.BAD_REQUEST);
        }
        Optional<Order> orderOpt = user.getOrders().stream()
                .filter(ord -> ord.getId().equals(orderId)).findAny();
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            List<Item> itemList = order.getItems();
            if (itemList == null) {
                itemList = new ArrayList<>(order.getItemGroups().size());
                order.setItems(itemList);
            }
            if (items.size() + itemList.size() > order.getItemGroups().size()) {
                return new ResponseEntity<>("Too much items added", HttpStatus.CONFLICT);
            }
            itemList.addAll(items);
            userRepo.save(user);
            return new ResponseEntity<>("Order updated", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Order not found", HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/collected/{orderId}")
    public ResponseEntity<Object> finishOrder(@PathVariable String orderId) {
        User user = userRepo.getUserByOrders_Id(orderId);
        if (user == null) {
            return new ResponseEntity<>("Order not found", HttpStatus.BAD_REQUEST);
        }
       /* if (user.getRoleSet() == null) {
            return new ResponseEntity<>("User does not have permission to finish order", HttpStatus.UNAUTHORIZED);
        }
        if (!user.getRoleSet().contains(new Role(ROLE_SHIFT_MANAGER))) {
            return new ResponseEntity<>("User does not have permission to finish order", HttpStatus.UNAUTHORIZED);
        }*/
        if (user.getOrders() == null) {
            return new ResponseEntity<>("Order not found", HttpStatus.BAD_REQUEST);
        }

        Optional<Order> orderOpt = user.getOrders().stream()
                .filter(ord -> ord.getId().equals(orderId)).findAny();
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            order.setTodo(false);
            order.setDateOfCollection(new Date());
            userRepo.save(user);
            return new ResponseEntity<>("Order status updated", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Order not found", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/worker/{workerId}")
    public ResponseEntity<Object> findWorkersAssignments(@PathVariable String workerId) {
        User work = userRepo.getUserById(workerId);
        if (work == null) {
            return new ResponseEntity("Worker does not exist", HttpStatus.BAD_REQUEST);
        }
        if (work.getRoleSet() == null) {
            return new ResponseEntity<>("User does not have roles assigned", HttpStatus.UNAUTHORIZED);
        }
        if (!work.getRoleSet().contains(new Role(ROLE_WORKER))) {
            return new ResponseEntity<>("User is not a worker", HttpStatus.UNAUTHORIZED);
        }
        List<User> users = userRepo.getUsersByOrders_Workers(work);
        List<Order> orders = users.stream()
                .flatMap(user -> {
                    if (user.getOrders() != null) {
                        return user.getOrders()
                                .stream()
                                .filter(order ->
                                        order.getWorkers()
                                                .stream()
                                                .anyMatch(worker -> worker.getId().equals(workerId))
                                );
                    }
                    return Stream.empty();
                }).collect(Collectors.toList());
        return new ResponseEntity(orders, HttpStatus.OK);
    }

    @GetMapping("/active/worker/{workerId}")
    public ResponseEntity<Object> findWorkersActiveAssignments(@PathVariable String workerId) {
        User work = userRepo.getUserById(workerId);
        if (work == null) {
            return new ResponseEntity("Worker does not exist", HttpStatus.BAD_REQUEST);
        }
        if (work.getRoleSet() == null) {
            return new ResponseEntity<>("User does not have roles assigned", HttpStatus.UNAUTHORIZED);
        }
        if (!work.getRoleSet().contains(new Role(ROLE_WORKER))) {
            return new ResponseEntity<>("User is not a worker", HttpStatus.UNAUTHORIZED);
        }
        List<User> users = userRepo.getUsersByOrders_Workers(work);
        List<Order> orders = users.stream()
                .flatMap(user -> {
                    if (user.getOrders() != null) {
                        return user.getOrders()
                                .stream()
                                .filter(order ->
                                        order.getWorkers()
                                                .stream()
                                                .anyMatch(worker -> worker.getId().equals(workerId))
                                );
                    }
                    return Stream.empty();
                })
                .filter(Order::isTodo)
                .collect(Collectors.toList());
        return new ResponseEntity(orders, HttpStatus.OK);
    }

    @PatchMapping("/{orderId}/assign/{workerId}")
    public ResponseEntity<Object> assignWorker(@PathVariable String orderId, @PathVariable String workerId) {
        User worker = userRepo.getUserById(workerId);

        if (worker != null) {
            if (worker.getRoleSet() != null) {
                if (worker.getRoleSet().contains(new Role(ROLE_WORKER))) {
                    User orderer = userRepo.getUserByOrders_Id(orderId);
                    Optional<Order> orderOpt = orderer.getOrders()
                            .stream()
                            .filter(order -> order.getId().equals(orderId))
                            .findFirst();
                    if (orderOpt.isPresent()) {
                        Order order = orderOpt.get();
                        Set<User> workers = order.getWorkers();
                        if (workers == null) {
                            workers = new HashSet<>();
                        }
                        workers.add(worker);
                        userRepo.save(orderer);
                        return new ResponseEntity<>("Worker assigned correctly", HttpStatus.OK);
                    }
                } else {
                    return new ResponseEntity<>("User is not a worker", HttpStatus.UNAUTHORIZED);
                }
            } else {
                return new ResponseEntity<>("User does not have roles assigned", HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>("Order not found", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Worker not found", HttpStatus.BAD_REQUEST);
    }


    private String generateId(User user, Order order, Date date) {
        return String.format("%s-%s-%s", user.getContactInfo().getEmail(), order.getItemGroups().size(), date.getTime());
    }


}

