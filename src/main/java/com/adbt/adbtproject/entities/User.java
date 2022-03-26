package com.adbt.adbtproject.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class User implements Serializable {

    @Id
    private String id;
    private String name;
    private String surname;
    /*@JsonIgnore*/
    private String password;

    @Indexed/*(unique = true)*/
    private ContactInfo contactInfo;
    @NonNull

    private Address address;

    @NonNull
    //@ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roleSet;

    //@ElementCollection(fetch = FetchType.EAGER)
    List<Order> orders;

}
