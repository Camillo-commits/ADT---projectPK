package com.adbt.adbtproject.repo;

import com.adbt.adbtproject.entities.Warehouse;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface WarehouseRepo extends MongoRepository<Warehouse, String> {

    Warehouse getWarehouseById(String id);
    List<Warehouse> getAllByAddressNotNull();
    void deleteById(String id);
    List<Warehouse> findAll();
}
