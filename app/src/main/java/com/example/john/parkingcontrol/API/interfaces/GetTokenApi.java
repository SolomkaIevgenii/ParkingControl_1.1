package com.example.john.parkingcontrol.API.interfaces;

import com.example.john.parkingcontrol.API.models.TokenRequest;
import com.example.john.parkingcontrol.API.models.TokenResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface GetTokenApi {

    @POST("api/token")
    Call<TokenResponse> getTokenAccess(@Body TokenRequest tokenRequest);
}
