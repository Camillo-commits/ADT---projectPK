package com.adbt.adbtproject.controller;


import com.adbt.adbtproject.entities.Centre;
import com.adbt.adbtproject.entities.Warehouse;
import com.adbt.adbtproject.repo.CentreRepo;
import com.adbt.adbtproject.repo.WarehouseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/centre")
public class CentreController {

    @Autowired
    CentreRepo centreRepo;

    @Autowired
    WarehouseRepo warehouseRepo;

    @PostMapping
    public ResponseEntity<Centre> addCentre(@RequestBody Centre centre) {
        Set<Warehouse> warehouses = centre.getWarehouses();
        if (warehouses != null) {
            List<Warehouse> warehouseList = warehouseRepo.saveAll(warehouses);
            centre.setWarehouses(new HashSet<>(warehouseList));
        }
        centre = centreRepo.save(centre);
        return new ResponseEntity(centre, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> addCentre(@RequestBody Centre centre, @PathVariable String id) {
        Optional<Centre> dbOptionalCentre = centreRepo.findById(id);
        if (dbOptionalCentre.isPresent()) {
            Centre dbCentre = dbOptionalCentre.get();
            Optional.ofNullable(centre.getAddress()).ifPresent(dbCentre::setAddress);
            Set<Warehouse> warehouses = centre.getWarehouses();
            if (warehouses != null) {
                warehouses.stream().filter(Objects::nonNull).forEach(warehouse -> {
                    warehouseRepo.save(warehouse);
                });
                dbCentre.setWarehouses(warehouses);
            }
            dbCentre = centreRepo.save(dbCentre);
            return new ResponseEntity<>(dbCentre.toString(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Id not correct", HttpStatus.BAD_REQUEST);
    }

}
