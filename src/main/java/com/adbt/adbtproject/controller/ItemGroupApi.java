package com.adbt.adbtproject.controller;

import com.adbt.adbtproject.repo.ItemGroupRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/items")
public class ItemGroupApi {

    @Autowired
    ItemGroupRepo itemsRepo;

    @GetMapping("/{id}")
    public ResponseEntity<Integer> getItemCount(@PathVariable String id) {
        return new ResponseEntity<>(Optional.of(itemsRepo.findById(id).get().allAvailability()).orElse(0), HttpStatus.OK);
    }

}
