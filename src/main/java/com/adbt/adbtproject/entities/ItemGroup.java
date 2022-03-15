package com.adbt.adbtproject.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
@Getter
@Setter
public class ItemGroup {

    @Id
    private String id;

    private String name;

    private Double price;

    private List<String> properties;

    Map<Warehouse, ShelfPosition> placement;

    public int allAvailability() {
        AtomicInteger count = new AtomicInteger(0);
        placement.values().forEach(shelf -> count.set(count.get() + shelf.getQuantity()));
        return count.get();
    }
}
