package com.adbt.adbtproject.entities;

import com.adbt.adbtproject.entities.validate.NameValidator;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;

@RequiredArgsConstructor
@Getter
@Setter
@Document("warehouse")
public class Warehouse {

    @Id
    private String id;

    @Indexed(unique = true)
    @com.mongodb.lang.NonNull
    @NonNull
    @NotEmpty(groups = NameValidator.class)
    private String name;
    @NonNull
    private Address address;
    @NonNull
    private ContactInfo contactInfo;

}
