package com.adbt.adbtproject.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
public class Order {

    @Id
    private String id;

    List<ItemGroup> itemGroups;

    List<Item> items;

}
