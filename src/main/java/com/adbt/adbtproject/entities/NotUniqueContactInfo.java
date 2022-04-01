package com.adbt.adbtproject.entities;

import com.adbt.adbtproject.entities.validate.EmailValidator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;

@NoArgsConstructor
@Getter
@Setter
@Validated
public class NotUniqueContactInfo {
    @Id
    private String id;

    private String telNumber;

    @Email(groups = EmailValidator.class)
    private String email;
}
