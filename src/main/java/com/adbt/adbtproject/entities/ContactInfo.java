package com.adbt.adbtproject.entities;


import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;

@RequiredArgsConstructor
public class ContactInfo {

    @Id
    private String id;

    private String telNumber;
    private String email;



}