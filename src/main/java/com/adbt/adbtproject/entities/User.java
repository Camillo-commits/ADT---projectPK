package com.adbt.adbtproject.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import java.util.List;
import java.util.Set;

@Document(collection = "users")
@RequiredArgsConstructor
@Getter
@Setter
public class User {

    @Id
    private  String id;
    private  String name;
    private  String surname;

    private ContactInfo contactInfo;
    private Address address;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roleSet;

    @ElementCollection(fetch = FetchType.EAGER)
    List<Order> orders;

}
