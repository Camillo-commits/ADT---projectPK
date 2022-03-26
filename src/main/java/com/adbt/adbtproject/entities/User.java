package com.adbt.adbtproject.entities;

import com.mongodb.lang.Nullable;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@Document(collection = "users")
public class User implements Serializable {

    @Id
    private String id;
    private String name;
    private String surname;
    private String password;

    @Indexed(unique = true)
    private ContactInfo contactInfo;

    @NonNull
    private Address address;

    @Nullable
    private Set<Role> roleSet;

    @Nullable
    private Set<Order> orders;

}
