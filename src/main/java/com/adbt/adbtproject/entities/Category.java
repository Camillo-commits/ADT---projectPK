package com.adbt.adbtproject.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
public class Category {
    @Id
    private String id;

    @Indexed(direction = IndexDirection.ASCENDING)
    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<ItemGroup> items;
}
