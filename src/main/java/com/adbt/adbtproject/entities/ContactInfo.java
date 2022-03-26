package com.adbt.adbtproject.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.io.Serializable;

@Getter
@Setter
public class ContactInfo implements Serializable {

    @Id
    private String id;
    //@Indexed(unique = true)
    @NonNull
    private String telNumber;
    //@Indexed(unique = true)
    @NonNull
    private String email;

}
