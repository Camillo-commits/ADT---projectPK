package com.adbt.adbtproject.requestUtils;

import com.adbt.adbtproject.entities.ContactInfo;
import com.adbt.adbtproject.entities.Order;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class OrderAddRequest {
    private Order order;
    private ContactInfo userDetails;
}
