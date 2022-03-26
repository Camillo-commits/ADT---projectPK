package com.adbt.adbtproject.entities;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
public class Centre {

    @Id
    private String id;

    @NonNull
    @ElementCollection(fetch = FetchType.EAGER)
    List<Warehouse> warehouseList;

    Address address;

}
