package com.adbt.adbtproject.controller;

import com.adbt.adbtproject.entities.ItemGroup;
import com.adbt.adbtproject.entities.User;
import com.adbt.adbtproject.entities.Warehouse;
import com.adbt.adbtproject.repo.ItemGroupRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @PostMapping
    public ResponseEntity<HttpStatus> createItemGroup(@RequestBody ItemGroup itemGroup) {
        itemsRepo.save(itemGroup);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteItemGroup(@PathVariable String id) {
        itemsRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ItemGroup>> getAll() {
        return new ResponseEntity<>(itemsRepo.findAll(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemGroup> updateItemGroup(@PathVariable String id, @RequestBody ItemGroup itemGroupRequest) {
        ItemGroup itemGroup = itemsRepo.findById(id).get();

        BeanUtils.copyProperties(itemGroupRequest, itemGroup);

        return ResponseEntity.ok(itemsRepo.save(itemGroup));
    }

}
