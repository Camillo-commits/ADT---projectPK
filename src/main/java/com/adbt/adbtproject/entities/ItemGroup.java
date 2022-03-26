package com.adbt.adbtproject.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
@Getter
@Setter
public class ItemGroup {

    @Id
    private String id;

    private String name;

    private Double price;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> properties;

    //TODO: why map does not work
    @ElementCollection(fetch = FetchType.EAGER)
    private Map<Warehouse, ShelfPosition> placement;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Vendor> vendors;

    public int allAvailability() {
        AtomicInteger count = new AtomicInteger(0);
        placement.values().forEach(shelf -> count.set(count.get() + shelf.getQuantity()));
        return count.get();
    }
}
