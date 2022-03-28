package com.adbt.adbtproject.requestUtils;

import com.adbt.adbtproject.entities.ItemGroup;
import com.adbt.adbtproject.entities.ShelfPosition;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;

@NoArgsConstructor
@Data
public class ItemGroupAddRequest {
    ItemGroup itemGroup;
    String warehouseName;
    ShelfPosition shelfPosition;
}
