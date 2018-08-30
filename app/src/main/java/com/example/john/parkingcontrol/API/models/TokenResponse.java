package com.example.john.parkingcontrol.API.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TokenResponse {

    @SerializedName("access_token")
    @Expose
    private String access_token;
    @SerializedName("login")
    @Expose
    private String login;

    public String getAccess_token() {
        return access_token;
    }

    public String getLogin() {
        return login;
    }
}
