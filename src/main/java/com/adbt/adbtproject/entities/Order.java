package com.adbt.adbtproject.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.sql.Date;

@NoArgsConstructor
@Getter
@Setter
public class Order {

    @Id
    private String id;

    private Date dateOfOrder;

    private Date dateOfCollection;

    private Date dateOfRetrieval;

    //TODO: fix adding user when workers itemGroups and items in order
/*    private Set<User> workers;

    private Set<ItemGroup> itemGroups;

    private Set<Item> items;*/

}
