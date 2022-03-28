package com.adbt.adbtproject.repo;

import com.adbt.adbtproject.entities.Category;
import com.adbt.adbtproject.entities.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepo extends MongoRepository<Order, String> {
}
