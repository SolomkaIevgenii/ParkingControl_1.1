package com.example.john.parkingcontrol.API.models.Guid;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GuidResponse {

    @SerializedName("guid")
    @Expose
    private String guid;

    public String getGuid() {
        return guid;
    }
}
