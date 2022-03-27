package com.adbt.adbtproject.controller;

import com.adbt.adbtproject.entities.ContactInfo;
import com.adbt.adbtproject.entities.User;
import com.adbt.adbtproject.entities.validate.EmailValidator;
import com.adbt.adbtproject.repo.UserRepo;
import com.mongodb.MongoWriteException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "api/users", consumes = {"*/*"})
public class UserApi {

    @Autowired
    UserRepo userRepo;

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userRepo.findAll(), HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<User>> getUserByName(@PathVariable String name) {
        return new ResponseEntity(userRepo.getUsersByName(name), HttpStatus.OK);
    }

    @GetMapping("/surname/{surname}")
    public ResponseEntity<List<User>> getUserBySurname(@PathVariable String surname) {
        return new ResponseEntity(userRepo.getUsersBySurname(surname), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        return new ResponseEntity(userRepo.findById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User userDetails) {
        User user = userRepo.findById(id).get();

        BeanUtils.copyProperties(userDetails, user);

        return ResponseEntity.ok(userRepo.save(user));
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @Valid User user,  BindingResult result) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<ContactInfo>> constraintViolations = validator.validate(user.getContactInfo(), EmailValidator.class);

        try {
            if (result.hasErrors() || !constraintViolations.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            userRepo.save(user);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable String id) {
        userRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
