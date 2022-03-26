package com.adbt.adbtproject.entities;


import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.io.Serializable;

@Getter
@Setter
public class ContactInfo implements Serializable {

    @Id
    private String id;
    @Indexed(unique = true)
    @NonNull
    private String telNumber;
    @Indexed(unique = true)
    @NonNull
    private String email;

    public void setEmail(String email) {
        if(email.contains("@")){
            this.email = email;
        }
    }
}
