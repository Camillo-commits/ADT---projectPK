package com.adbt.adbtproject.requestUtils;

import com.adbt.adbtproject.entities.Centre;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddCentreRequest {

    private Centre centre;
    private String[] warehouseNames;

}
