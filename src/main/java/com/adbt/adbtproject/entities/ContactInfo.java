package com.adbt.adbtproject.entities;


import com.adbt.adbtproject.entities.validate.EmailValidator;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;

import java.io.Serializable;

@Getter
@Setter
@Validated
public class ContactInfo implements Serializable {

    @Id
    private String id;

    @Indexed(unique = true)
    @NonNull
    private String telNumber;

    @Indexed(unique = true)
    @Email(groups = EmailValidator.class)
    @NonNull
    private String email;
}
