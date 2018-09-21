package com.example.john.parkingcontrol.API.models.AddCarInc;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddCarIncResponse {
    @SerializedName("isSuccess")
    @Expose
    private Boolean isSuccess;

    @SerializedName("errorMsg")
    @Expose
    private String errorMsg;

    @SerializedName("detailErrorMsg")
    @Expose
    private String detailErrorMsg;

    @SerializedName("document_number")
    @Expose
    private String document_number;

    @SerializedName("document_author")
    @Expose
    private String document_author;

    @SerializedName("document_date")
    @Expose
    private String document_date;

    public String getDocument_number() {return document_number;}

    public String getDocument_author() {return document_author;}

    public String getDocument_date() {return document_date;}

    public Boolean getIsSuccess() {
        return isSuccess;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public String getDetailErrorMsg() {
        return detailErrorMsg;
    }
}
