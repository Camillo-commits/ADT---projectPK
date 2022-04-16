package com.adbt.adbtproject.entities;

import com.mongodb.lang.NonNull;
import com.mongodb.lang.Nullable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Date;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class Order {

    @NonNull
    private String id;

    private Date dateOfOrder;

    @Nullable
    private Date dateOfCollection;

    @Nullable
    private Date dateOfRetrieval;

    private boolean todo;

    private double totalPrice;

    @DocumentReference(collection = "users", lazy = true)
    private Set<User> workers;

    private List<NotUniqueItemGroup> itemGroups;

    private List<Item> items;

}
