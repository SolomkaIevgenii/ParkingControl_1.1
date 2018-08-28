package com.example.john.parkingcontrol.API.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TokenResponse {

    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("name")
    @Expose
    private String login;

    public String getToken() {
        return token;
    }

    public String getLogin() {
        return login;
    }
}
