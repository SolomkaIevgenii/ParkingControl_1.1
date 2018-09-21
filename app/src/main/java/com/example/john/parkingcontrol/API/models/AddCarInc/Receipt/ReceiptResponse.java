package com.example.john.parkingcontrol.API.models.AddCarInc.Receipt;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReceiptResponse {
    @SerializedName("Guid")
    @Expose
    private String Guid;

    @SerializedName("Text")
    @Expose
    private String Text;

    public String getGuid() {
        return Guid;
    }

    public String getText() {
        return Text;
    }
}
