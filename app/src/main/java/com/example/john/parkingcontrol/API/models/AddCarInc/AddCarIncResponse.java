package com.example.john.parkingcontrol.API.models.AddCarInc;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddCarIncResponse {
    @SerializedName("isSuccess")
    @Expose
    private String isSuccess;

    @SerializedName("errorMsg")
    @Expose
    private String errorMsg;

    public String getIsSuccess() {
        return isSuccess;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public String getDetailErrorMsg() {
        return detailErrorMsg;
    }

    @SerializedName("detailErrorMsg")
    @Expose
    private String detailErrorMsg;
}
