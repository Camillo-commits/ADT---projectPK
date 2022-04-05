package com.adbt.adbtproject.controller;

import com.adbt.adbtproject.entities.Category;
import com.adbt.adbtproject.entities.ItemGroup;
import com.adbt.adbtproject.repo.CategoryRepo;
import com.adbt.adbtproject.repo.ItemGroupRepo;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CetegoryApi {

    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    ItemGroupRepo itemGroupRepo;

    @PutMapping("/{categoryName}/item/{itemName}")
    public ResponseEntity<Object> addItemToCategory(@PathVariable String categoryName, @PathVariable String itemName) {
        Category category = categoryRepo.getCategoryByName(categoryName);
        if (category != null) {
            ItemGroup item = itemGroupRepo.getItemGroupByName(itemName);
            List<ItemGroup> items = category.getItems();
            if (items == null) {
                items = new ArrayList<>();
                category.setItems(items);
            }
            items.add(item);
            categoryRepo.save(category);
            return new ResponseEntity<>("Item added", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Category not found", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{categoryName}/items")
    public ResponseEntity<Object> addItemsToCategory(@PathVariable String categoryName, @RequestBody List<String> itemNames) {
        Category category = categoryRepo.getCategoryByName(categoryName);
        if (category != null) {
            List<ItemGroup> items = category.getItems();
            if (items == null) {
                items = new ArrayList<>();
                category.setItems(items);
            }
            for (String itemName : itemNames) {
                ItemGroup item = itemGroupRepo.getItemGroupByName(itemName);
                items.add(item);
            }
            categoryRepo.save(category);
            return new ResponseEntity<>("Items added", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Category not found", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{categoryName}/item/{itemName}")
    public ResponseEntity<Object> deleteItemFromCategory(@PathVariable String categoryName, @PathVariable String itemName) {
        Category category = categoryRepo.getCategoryByName(categoryName);
        if (category != null) {
            List<ItemGroup> items = category.getItems();
            if (items == null) {
                return new ResponseEntity<>("Category does not have items", HttpStatus.CONFLICT);
            }

            ItemGroup item = itemGroupRepo.getItemGroupByName(itemName);
            if(items.remove(item)){
                categoryRepo.save(category);
                return new ResponseEntity<>("Item removed", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Item could not be removed, try again later", HttpStatus.EXPECTATION_FAILED);
            }

        } else {
            return new ResponseEntity<>("Category not found", HttpStatus.BAD_REQUEST);
        }
    }

}
