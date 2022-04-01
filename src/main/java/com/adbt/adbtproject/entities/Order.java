package com.adbt.adbtproject.entities;

import com.mongodb.lang.NonNull;
import com.mongodb.lang.Nullable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Date;
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

    @DocumentReference(collection = "users", lazy = true)
    private Set<User> workers;

    private Set<ItemGroup> itemGroups;

    private Set<Item> items;

}
