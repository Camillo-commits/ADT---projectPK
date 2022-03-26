package com.adbt.adbtproject.repo;

import com.adbt.adbtproject.entities.Address;
import com.adbt.adbtproject.entities.Centre;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CentreRepo extends MongoRepository<Centre, String> {

    Centre getAllByAddressNotNull();
    Centre getCentreByAddress(Address address);
    List<Centre> findAll();
}
