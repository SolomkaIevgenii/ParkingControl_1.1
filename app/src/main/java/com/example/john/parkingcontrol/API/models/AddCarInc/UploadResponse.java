package com.example.john.parkingcontrol.API.models.AddCarInc;

import com.google.gson.annotations.SerializedName;

public class UploadResponse {

    @SerializedName("Guid")
    private String guid;

    @SerializedName("file")
    private String file;


    @SerializedName("isSuccess")
    private boolean isSuccess;

    @SerializedName("errorMsg")
    private String errorMsg;

    @SerializedName("detailErrorMsg")
    private String detailErrorMsg;

    public boolean getIsSuccess() {
        return isSuccess;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public String getDetailErrorMsg() {
        return detailErrorMsg;
    }
}
