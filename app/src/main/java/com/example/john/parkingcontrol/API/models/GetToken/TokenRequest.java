package com.example.john.parkingcontrol.API.models.GetToken;

public class TokenRequest {

    private String login;
    private String password;
    private String deviceInfo;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDeviceInfo() {return deviceInfo;}

    public void setDeviceInfo(String deviceInfo) {this.deviceInfo = deviceInfo;}
}
