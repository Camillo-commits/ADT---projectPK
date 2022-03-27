package com.adbt.adbtproject.controller;

import com.adbt.adbtproject.entities.*;
import com.adbt.adbtproject.repo.CategoryRepo;
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

    @Autowired
    CategoryRepo categoryRepo;

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

    @GetMapping("/percentageByCategory/{id}")
    public ResponseEntity<String> getPercentageOfItemGroupByCategory(@PathVariable String id) {
        Optional<Category> category = categoryRepo.findById(id);
        int amountOfItemGroupsByCategory = category.get().getItems().size();
        int amountOfAllItemGroups = itemsRepo.findAll().size();

        if(amountOfAllItemGroups == 0){
            return new ResponseEntity<>("No itemGroups found", HttpStatus.NO_CONTENT);
        }
        else if(amountOfItemGroupsByCategory == 0){
            return new ResponseEntity<>("No itemGroups found for selected category", HttpStatus.NO_CONTENT);
        }
        else{
            double percent = (((double) amountOfItemGroupsByCategory)/amountOfAllItemGroups) * 100;
            return new ResponseEntity<>(percent + "%", HttpStatus.OK);
        }
    }

}
