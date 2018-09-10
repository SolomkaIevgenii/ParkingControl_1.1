package com.example.john.parkingcontrol.API.interfaces;

import com.example.john.parkingcontrol.API.models.AddCarInc.AddCarIncRequest;
import com.example.john.parkingcontrol.API.models.AddCarInc.AddCarIncResponse;
import com.example.john.parkingcontrol.API.models.ListCarIncRequest;
import com.example.john.parkingcontrol.API.models.ListCarIncResponse;
import com.example.john.parkingcontrol.API.models.CheckCar.CheckCarResponse;
import com.example.john.parkingcontrol.API.models.CheckCar.CheckCarRequest;
import com.example.john.parkingcontrol.API.models.GetToken.TokenRequest;
import com.example.john.parkingcontrol.API.models.GetToken.TokenResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

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
    Call<CheckCarResponse> getPaymentStatus(@Body CheckCarRequest paymentStatusRequest);

    //Получение токена через from-data
    @FormUrlEncoded
    @POST("api/token")
    Call<TokenResponse> getTokenAccessPost(@Field("login") String superLogin, @Field("password") String superPassword);

    @Multipart
    @POST("api/?????")
    Call<ResponseBody> uploadPhoto(@Header("Authorization") String token, @Part("fileName")RequestBody fileName, @Part MultipartBody.Part photo, @Part("fileName")RequestBody fileName1, @Part MultipartBody.Part photo1);
}
