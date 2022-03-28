package com.adbt.adbtproject.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Set;

@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document("Centre")
public class Centre {

    @Id
    private String id;

    @NonNull
    @DocumentReference(collection = "warehouse")
    Set<Warehouse> warehouseList;

    @Indexed(unique = true)
    Address address;

}
