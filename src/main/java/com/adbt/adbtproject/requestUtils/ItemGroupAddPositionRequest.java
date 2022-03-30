package com.adbt.adbtproject.requestUtils;

import com.adbt.adbtproject.entities.ShelfPosition;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ItemGroupAddPositionRequest {

    private String warehouseName;
    private ShelfPosition shelfPosition;

}
