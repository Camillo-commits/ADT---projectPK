package com.adbt.adbtproject.entities;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@RequiredArgsConstructor
@Getter
@Setter
public class Warehouse {

    @Id
    private String id;
    @NonNull
    private Address address;
    @NonNull
    private ContactInfo contactInfo;

}
