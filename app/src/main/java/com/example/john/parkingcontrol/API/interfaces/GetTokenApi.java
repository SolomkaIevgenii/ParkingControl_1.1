package com.example.john.parkingcontrol.API.interfaces;

import com.example.john.parkingcontrol.API.models.TokenRequest;
import com.example.john.parkingcontrol.API.models.TokenResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface GetTokenApi {

    @POST("api/token")
    Call<TokenResponse> getTokenAccess(@Body TokenRequest tokenRequest);

    @FormUrlEncoded
    @POST("api/token")
    Call<TokenResponse> getTokenAccessPost(@Field("login") String superLogin, @Field("password") String superPassword);
}
