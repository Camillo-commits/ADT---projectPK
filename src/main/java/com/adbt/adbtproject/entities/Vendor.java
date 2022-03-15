package com.adbt.adbtproject.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@RequiredArgsConstructor
@Getter
@Setter
public class Vendor {

    @Id
    private String id;

    private String NIP;

    private String name;

    private ContactInfo contactInfo;
    private Address address;
}
