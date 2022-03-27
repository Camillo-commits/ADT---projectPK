package com.adbt.adbtproject.entities;


import com.adbt.adbtproject.entities.validate.EmailValidator;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;

import java.io.Serializable;

@NoArgsConstructor
@RequiredArgsConstructor
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
