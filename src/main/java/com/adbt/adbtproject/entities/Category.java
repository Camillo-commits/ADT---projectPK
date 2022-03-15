package com.adbt.adbtproject.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
public class Category {
    @Id
    private String id;

    private String name;

    private List<ItemGroup> items;
}
