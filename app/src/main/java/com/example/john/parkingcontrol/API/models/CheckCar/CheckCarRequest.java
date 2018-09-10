package com.example.john.parkingcontrol.API.models.CheckCar;

public class CheckCarRequest {

    private String token;
    private String carnumber;

    public String getCarnumber() {
        return carnumber;
    }

    public void setCarnumber(String carnumber) {
        this.carnumber = carnumber;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
