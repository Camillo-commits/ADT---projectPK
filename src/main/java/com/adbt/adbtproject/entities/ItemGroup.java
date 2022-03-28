package com.adbt.adbtproject.entities;

import com.mongodb.BasicDBObject;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
@Getter
@Setter
@Document("Items")
public class ItemGroup {

    @Id
    private String id;

    @Indexed(unique = true)
    private String name;

    private Double price;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> properties;

    private Set<Position> placement;

    private Set<Vendor> vendors;

    public int allAvailability() {
        AtomicInteger count = new AtomicInteger(0);
        placement.forEach(position -> {
            count.set(count.get() + position.getValue().getQuantity());
        });
        return count.get();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Position {
        @DocumentReference(collection = "warehouse")
        private Warehouse key;
        private ShelfPosition value;
    }
}
