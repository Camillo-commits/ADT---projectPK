package com.adbt.adbtproject.repo;

import com.adbt.adbtproject.entities.Category;
import com.adbt.adbtproject.entities.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepo extends MongoRepository<Category, String> {

    Category getCategoryById(String id);
}