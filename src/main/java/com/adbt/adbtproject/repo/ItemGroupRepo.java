package com.adbt.adbtproject.repo;

import com.adbt.adbtproject.entities.ItemGroup;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ItemGroupRepo extends MongoRepository<ItemGroup, String> {

    ItemGroup getItemGroupByName(String name);

    List<ItemGroup> getItemGroupsByPriceBetween(Double lower, Double upper);

    ItemGroup getItemGroupById(String id);

}
