package com.example.john.parkingcontrol.API.interfaces;

import com.example.john.parkingcontrol.API.models.AddCarIncRequest;
import com.example.john.parkingcontrol.API.models.AddCarIncResponse;
import com.example.john.parkingcontrol.API.models.ListCarIncRequest;
import com.example.john.parkingcontrol.API.models.ListCarIncResponse;
import com.example.john.parkingcontrol.API.models.TokenRequest;
import com.example.john.parkingcontrol.API.models.TokenResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface GetTokenApi {

    @POST("api/token")
    Call<TokenResponse> getTokenAccess(@Header("Authorization") String token, @Body TokenRequest tokenRequest);

    @POST("api/add_car_incident")
    Call<AddCarIncResponse> getTokenAccess(@Body AddCarIncRequest addCarIncRequest);

    @POST("api/token")
    Call<ListCarIncResponse> getTokenAccess(@Header("Authorization") String token, @Body ListCarIncRequest listCarIncRequest);

    @FormUrlEncoded
    @POST("api/token")
    Call<TokenResponse> getTokenAccessPost(@Field("login") String superLogin, @Field("password") String superPassword);
}
