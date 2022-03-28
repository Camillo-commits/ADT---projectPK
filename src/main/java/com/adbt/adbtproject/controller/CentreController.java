package com.adbt.adbtproject.controller;


import com.adbt.adbtproject.entities.Centre;
import com.adbt.adbtproject.entities.Warehouse;
import com.adbt.adbtproject.repo.CentreRepo;
import com.adbt.adbtproject.repo.WarehouseRepo;
import com.adbt.adbtproject.requestUtils.AddCentreRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/centre")
public class CentreController {

    @Autowired
    CentreRepo centreRepo;

    @Autowired
    WarehouseRepo warehouseRepo;

    @PostMapping
    public ResponseEntity<Centre> addCentre(@RequestBody AddCentreRequest request) {
        String[] warehouseNames = request.getWarehouseNames();
        Centre centre = request.getCentre();
        Set<Warehouse> warehouseList = new HashSet<>();
        for(String name : warehouseNames) {
            warehouseList.add(warehouseRepo.getWarehouseByName(name));
        }
        centre.setWarehouseList(warehouseList);
        centreRepo.save(centre);
        return new ResponseEntity(centre, HttpStatus.CREATED);
    }

}
