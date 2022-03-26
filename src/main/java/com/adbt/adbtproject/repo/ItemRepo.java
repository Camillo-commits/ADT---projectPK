package com.adbt.adbtproject.repo;

import com.adbt.adbtproject.entities.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ItemRepo extends MongoRepository<Item, String> {
    Item getItemById(String id);
}
