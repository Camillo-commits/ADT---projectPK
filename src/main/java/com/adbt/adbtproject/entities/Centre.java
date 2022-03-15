package com.adbt.adbtproject.entities;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
public class Centre {

    @Id
    private String id;
    @NonNull
    List<Warehouse> warehouseList;

}
