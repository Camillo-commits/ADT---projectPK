package com.adbt.adbtproject.repo;

import com.adbt.adbtproject.entities.Warehouse;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WarehouseRepo extends MongoRepository<Warehouse, String> {
}
