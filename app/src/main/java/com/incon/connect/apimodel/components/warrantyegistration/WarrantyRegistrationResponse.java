package com.incon.connect.apimodel.components.warrantyegistration;

/**
 * Created by PC on 10/5/2017.
 */

public class WarrantyRegistrationResponse {
    public WarrantyRegistrationResponse(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    String name;
}
