package com.example.john.parkingcontrol.API.models.CheckCar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckCarResponse {

    @SerializedName("onParking")
    @Expose
    private Boolean onParking;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("carNumber")
    @Expose
    private String carNumber;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("parkigName")
    @Expose
    private String parkigName;

    @SerializedName("parkingStart")
    @Expose
    private String parkingStart;

    @SerializedName("parkingEnd")
    @Expose
    private String parkingEnd;

    @SerializedName("PrepayHours")
    @Expose
    private int PrepayHours;

    public Boolean getOnParking() {
        return onParking;
    }

    public String getMessage() {
        return message;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getParkigName() {
        return parkigName;
    }

    public String getParkingStart() {
        return parkingStart;
    }

    public String getParkingEnd() {
        return parkingEnd;
    }

    public int getPrepayHours() {
        return PrepayHours;
    }
}