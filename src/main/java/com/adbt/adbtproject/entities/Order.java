package com.adbt.adbtproject.entities;

import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;

@RequiredArgsConstructor
public class Order {

    @Id
    private String id;

}
