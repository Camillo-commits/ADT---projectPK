package com.adbt.adbtproject.controller;

import com.adbt.adbtproject.entities.Address;
import com.adbt.adbtproject.entities.ContactInfo;
import com.adbt.adbtproject.entities.Warehouse;
import com.adbt.adbtproject.repo.WarehouseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import com.adbt.adbtproject.entities.Warehouse;
import com.adbt.adbtproject.repo.WarehouseRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/warehouse")
public class WarehouseApi {

    @Autowired
    private WarehouseRepo warehouseRepo;

    @GetMapping
    public ResponseEntity<List<Warehouse>> getAll() {
        return new ResponseEntity<>(warehouseRepo.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Warehouse> getById(@PathVariable String id) {
        return new ResponseEntity<>(warehouseRepo.findById(id).get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createWarehouse(@RequestBody Warehouse warehouse) {
        warehouseRepo.save(warehouse);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Warehouse> updateWarehouse(@PathVariable String id, @RequestBody Warehouse warehouse) {
        Warehouse warehouseNew = warehouseRepo.findById(id).get();
        BeanUtils.copyProperties(warehouse, warehouseNew);
        warehouseNew.setId(id);

        return ResponseEntity.ok(warehouseRepo.save(warehouseNew));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteWarehouse(@PathVariable String id) {
        warehouseRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<Warehouse>> getWarehouseByCity(@PathVariable String city) {
        List<Warehouse> wList = warehouseRepo.findAll().stream().filter(w -> w.getAddress().getCity().equals(city)).collect(Collectors.toList());
        return new ResponseEntity<>(wList, HttpStatus.OK);
    }
}
