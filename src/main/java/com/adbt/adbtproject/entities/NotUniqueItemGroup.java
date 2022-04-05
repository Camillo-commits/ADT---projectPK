package com.adbt.adbtproject.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Data
@NoArgsConstructor
public class NotUniqueItemGroup {
    @Id
    private String id;

    private String name;

    private Double price;

    private Set<String> properties;

    private Set<ItemGroup.Position> placement;

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

    public NotUniqueItemGroup(ItemGroup itemGroup) {
        this.id = itemGroup.getId();
        this.name = itemGroup.getName();
        this.placement = itemGroup.getPlacement();
        this.price = itemGroup.getPrice();
        this.properties = itemGroup.getProperties();
        this.vendors = itemGroup.getVendors();
    }
}
