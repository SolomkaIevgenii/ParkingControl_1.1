package com.example.john.parkingcontrol.API.models.AddCarInc.AddressByGPS;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddressResponse {
    @SerializedName("address")
    @Expose
    private String address;

    public String getAddress() {return address;}
}
