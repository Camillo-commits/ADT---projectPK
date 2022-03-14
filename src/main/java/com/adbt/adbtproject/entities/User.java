package com.adbt.adbtproject.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {

    @Id
    private @Getter String id;
    private @Getter @Setter String name;
    private @Getter @Setter String surname;

    public User(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

}
