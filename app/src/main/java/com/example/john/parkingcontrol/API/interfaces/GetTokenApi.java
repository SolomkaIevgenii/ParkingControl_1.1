package com.example.john.parkingcontrol.API.interfaces;

import com.example.john.parkingcontrol.API.models.AddCarIncRequest;
import com.example.john.parkingcontrol.API.models.AddCarIncResponse;
import com.example.john.parkingcontrol.API.models.ListCarIncRequest;
import com.example.john.parkingcontrol.API.models.ListCarIncResponse;
import com.example.john.parkingcontrol.API.models.PaymentStatusResponse;
import com.example.john.parkingcontrol.API.models.PaymentStatusRequest;
import com.example.john.parkingcontrol.API.models.TokenRequest;
import com.example.john.parkingcontrol.API.models.TokenResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface GetTokenApi {

    //Получение токена через Json
    @POST("api/token")
    Call<TokenResponse> getTokenAccess(@Body TokenRequest tokenRequest);

    //Создать инцидент
    @POST("api/add_car_incident")
    Call<AddCarIncResponse> addNewIncident(@Header("Authorization") String token, @Body AddCarIncRequest addCarIncRequest);

    @POST("api/token")
    Call<ListCarIncResponse> get(@Header("Authorization") String token, @Body ListCarIncRequest listCarIncRequest);

    //Статус оплаты за парковку
    @POST("ExtApi/CheckCar")
    Call<PaymentStatusResponse> getPaymentStatus(@Body PaymentStatusRequest paymentStatusRequest);

    //Получение токена через from-data
    @FormUrlEncoded
    @POST("api/token")
    Call<TokenResponse> getTokenAccessPost(@Field("login") String superLogin, @Field("password") String superPassword);
}
