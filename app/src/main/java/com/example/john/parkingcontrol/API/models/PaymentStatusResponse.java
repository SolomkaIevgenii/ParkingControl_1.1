package com.example.john.parkingcontrol.API.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentStatusResponse {

    @SerializedName("onparking")
    @Expose
    private Boolean onparking;

    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getOnparking() {return onparking;}

    public String getResponseMessage() {return message;}

}