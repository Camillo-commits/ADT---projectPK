package com.adbt.adbtproject.repo;

import com.adbt.adbtproject.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends MongoRepository<User, String> {

    User getUserById(String id);

    User getUserByContactInfo_Email(String email);

    List<User> getUsersByName(String name);

    List<User> getUsersBySurname(String surname);

    List<User> getUsersByOrders_TodoIsTrue();

    User getUserByOrders_Id(String id);

    List<User> getUsersByOrders_Workers(User worker);

    List<User> getUsersByAddress_Country(String country);
}
