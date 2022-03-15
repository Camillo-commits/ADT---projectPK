package com.adbt.adbtproject.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@RequiredArgsConstructor
@Getter
@Setter
public class ShelfPosition {

    @Id
    private String id;

    private String name;

    private long index;

    private int quantity;

}
