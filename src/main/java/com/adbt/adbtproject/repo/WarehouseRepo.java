package com.adbt.adbtproject.repo;

import com.adbt.adbtproject.entities.Warehouse;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

@Hidden
public interface WarehouseRepo extends MongoRepository<Warehouse, String> {

    Warehouse getWarehouseById(String id);
    List<Warehouse> getAllByAddressNotNull();
    void deleteById(String id);
    List<Warehouse> findAll();
    Warehouse getWarehouseByName(String name);
}
