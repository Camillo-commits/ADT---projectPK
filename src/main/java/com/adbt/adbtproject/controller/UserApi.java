package com.adbt.adbtproject.controller;

import com.adbt.adbtproject.entities.User;
import com.adbt.adbtproject.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserApi {

    @Autowired
    UserRepo userRepo;

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userRepo.findAll(), HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<User>> getUserByName(@PathVariable String name) {
        return new ResponseEntity<>(userRepo.getUsersByName(name), HttpStatus.OK);
    }

    @GetMapping("/surname/{surname}")
    public ResponseEntity<List<User>> getUserBySurname(@PathVariable String surname) {
        return new ResponseEntity<>(userRepo.getUsersBySurname(surname), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        return new ResponseEntity<>(userRepo.getUserById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User userDetails) {
        User user = userRepo.getUserById(id);

        user.setName(userDetails.getName());
        user.setSurname(userDetails.getSurname());

        return ResponseEntity.ok(userRepo.save(user));
    }

    @PostMapping
    public User createEmployee(@RequestBody User user) {
        return userRepo.save(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable String id) {
        userRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
