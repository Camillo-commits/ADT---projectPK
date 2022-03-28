package com.adbt.adbtproject.controller;

import com.adbt.adbtproject.entities.*;
import com.adbt.adbtproject.repo.CategoryRepo;
import com.adbt.adbtproject.repo.ItemGroupRepo;
import com.adbt.adbtproject.repo.WarehouseRepo;
import com.adbt.adbtproject.requestUtils.ItemGroupAddPositionRequest;
import com.adbt.adbtproject.requestUtils.ItemGroupAddRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/items")
public class ItemGroupApi {

    @Autowired
    ItemGroupRepo itemsRepo;

    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    WarehouseRepo warehouseRepo;

    @GetMapping("/{id}")
    public ResponseEntity<Integer> getItemCount(@PathVariable String id) {
        return new ResponseEntity<>(Optional.of(itemsRepo.findById(id).get().allAvailability()).orElse(0), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createItemGroup(@RequestBody ItemGroupAddRequest request) {
        String warehouseName = request.getWarehouseName();
        ItemGroup itemGroup = request.getItemGroup();
        ShelfPosition shelfPosition = request.getShelfPosition();
        Warehouse warehouse = warehouseRepo.getWarehouseByName(warehouseName);
        Set<ItemGroup.Position> positions = itemGroup.getPlacement();
        if(positions == null) {
            positions = new HashSet<>();
            positions.add(new ItemGroup.Position(warehouse, shelfPosition));
            itemGroup.setPlacement(positions);
        } else {
            positions.add(new ItemGroup.Position(warehouse, shelfPosition));
        }

        itemsRepo.save(itemGroup);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/position")
    public ResponseEntity<HttpStatus> addPostion(@RequestBody ItemGroupAddPositionRequest request) {
        String itemName = request.getItemName();
        String warehouseName = request.getWarehouseName();
        ShelfPosition shelfPosition = request.getShelfPosition();
        ItemGroup itemGroup = itemsRepo.getItemGroupByName(itemName);
        Warehouse warehouse = warehouseRepo.getWarehouseByName(warehouseName);
        Set<ItemGroup.Position> positions = itemGroup.getPlacement();
        if(positions == null) {
            positions = new HashSet<>();
            positions.add(new ItemGroup.Position(warehouse, shelfPosition));
            itemGroup.setPlacement(positions);
        } else {
            positions.add(new ItemGroup.Position(warehouse, shelfPosition));
        }
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
