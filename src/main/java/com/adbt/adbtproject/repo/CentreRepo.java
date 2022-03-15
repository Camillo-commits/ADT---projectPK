package com.adbt.adbtproject.repo;

import com.adbt.adbtproject.entities.Centre;
import com.adbt.adbtproject.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CentreRepo extends MongoRepository<Centre, String> {

}
