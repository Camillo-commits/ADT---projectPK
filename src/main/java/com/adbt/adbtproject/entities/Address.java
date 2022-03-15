package com.adbt.adbtproject.entities;


import com.mongodb.lang.Nullable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@RequiredArgsConstructor
@Getter
@Setter
public class Address {

    @Id
    private String id;

    private String Country;
    private String city;
    private String street;
    private String houseNumber;
    @Nullable
    private String apartmentNumber;
    private String postalCode;

}
