package com.adbt.adbtproject.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.sql.Date;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class Order {

    @Id
    private String id;

    private Date dateOfOrder;

    private Date dateOfCollection;

    private Date dateOfRetrieval;

    @DocumentReference(collection = "users", lazy = true)
    private Set<User> workers;

    @DocumentReference(lazy = true)
    private Set<ItemGroup> itemGroups;

    private Set<Item> items;

}
