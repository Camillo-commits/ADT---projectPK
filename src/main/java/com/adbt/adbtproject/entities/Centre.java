package com.adbt.adbtproject.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Document("centres")
public class Centre {

    @Id
    private String id;

    @DocumentReference(collection = "warehouse")
    Set<Warehouse> warehouses;

    @Indexed(unique = true)
    Address address;

}
