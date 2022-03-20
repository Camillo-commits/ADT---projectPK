package com.adbt.adbtproject.repo;

import com.adbt.adbtproject.entities.ItemGroup;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ItemGroupRepo extends MongoRepository<ItemGroup, String> {
}
