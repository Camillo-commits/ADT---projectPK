package com.adbt.adbtproject.entities;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

import io.swagger.models.Contact;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.repository.cdi.Eager;

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

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roleSet;

    public User(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }


}
