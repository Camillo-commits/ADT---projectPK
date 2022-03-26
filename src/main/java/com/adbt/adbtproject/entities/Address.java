package com.adbt.adbtproject.entities;


import com.mongodb.lang.Nullable;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@RequiredArgsConstructor
@Getter
@Setter
public class Address {

    @Id
    private String id;
    @NonNull
    private String country;
    @NonNull
    private String city;
    @NonNull
    private String street;
    @NonNull
    private String houseNumber;
    @Nullable
    private String apartmentNumber;
    @NonNull
    private String postalCode;

}
