package com.adbt.adbtproject.entities;

import lombok.Getter;
import lombok.NonNull;
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
    private String id;
    @NonNull
    private String name;
    @NonNull
    private String surname;
    @NonNull
    private ContactInfo contactInfo;
    @NonNull
    private Address address;

    @ElementCollection(fetch = FetchType.EAGER)
    @NonNull
    private Set<Role> roleSet;

    @ElementCollection(fetch = FetchType.EAGER)
    List<Order> orders;

}
