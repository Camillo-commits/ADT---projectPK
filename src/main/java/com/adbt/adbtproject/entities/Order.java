package com.adbt.adbtproject.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import java.sql.Date;
import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
public class Order {

    @Id
    private String id;

    private Date dateOfOrder;

    private Date dateOfCollection;

    private Date dateOfRetrieval;

    private List<User> workers;

    @ElementCollection(fetch = FetchType.EAGER)
    List<ItemGroup> itemGroups;

    @ElementCollection(fetch = FetchType.EAGER)
    List<Item> items;

}
