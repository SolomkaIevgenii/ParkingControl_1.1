package com.example.john.parkingcontrol.API.models.AddCarInc.Receipt;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReceiptResponse {
    @SerializedName("guid")
    @Expose
    private String guid;

    @SerializedName("text")
    @Expose
    private String text;

    public String getGuid() {
        return guid;
    }

    public String getText() {
        return text;
    }
}
