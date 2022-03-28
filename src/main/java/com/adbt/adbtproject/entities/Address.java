package com.adbt.adbtproject.entities;


import com.adbt.adbtproject.entities.validate.NameValidator;
import com.mongodb.lang.Nullable;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotEmpty;

@RequiredArgsConstructor
@Getter
@Setter
public class Address {

    @Id
    private String id;
    @NonNull
    @NotEmpty(groups = NameValidator.class)
    private String country;
    @NonNull
    @NotEmpty(groups = NameValidator.class)
    private String city;
    @NonNull
    @NotEmpty(groups = NameValidator.class)
    private String street;
    @NonNull
    @NotEmpty(groups = NameValidator.class)
    private String houseNumber;
    @Nullable
    private String apartmentNumber;
    @NonNull
    @NotEmpty(groups = NameValidator.class)
    private String postalCode;

}
