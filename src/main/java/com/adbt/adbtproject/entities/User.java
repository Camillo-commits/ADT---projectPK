package com.adbt.adbtproject.entities;

import com.mongodb.lang.Nullable;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@Document(collection = "users")
public class User implements Serializable {

    @Id
    private String id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String surname;
    @NotEmpty
    private String password;

    @Indexed(unique = true)
    @NotNull
    private ContactInfo contactInfo;

    @NonNull
    private Address address;

    @Nullable
    private Set<Role> roleSet;

    @Nullable
    private Set<Order> orders;

}
