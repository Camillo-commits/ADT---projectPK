package com.adbt.adbtproject.entities;


import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@RequiredArgsConstructor
@Getter
@Setter
public class ContactInfo {

    @Id
    private String id;
    @NonNull
    private String telNumber;
    @NonNull
    private String email;

}
