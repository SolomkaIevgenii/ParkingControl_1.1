package com.example.john.parkingcontrol.API.interfaces;

import android.content.Intent;

import com.example.john.parkingcontrol.API.models.AddCarInc.AddCarIncRequest;
import com.example.john.parkingcontrol.API.models.AddCarInc.AddCarIncResponse;
import com.example.john.parkingcontrol.API.models.AddCarInc.Receipt.ReceiptRequest;
import com.example.john.parkingcontrol.API.models.AddCarInc.Receipt.ReceiptResponse;
import com.example.john.parkingcontrol.API.models.AddCarInc.UploadResponse;
import com.example.john.parkingcontrol.API.models.Guid.GuidResponse;
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

    //Получить Guid
    @POST("api/guid")
    Call<GuidResponse> getGuid(@Header("Authorization") String token);

    //Создать инцидент
    @POST("api/add_car_incident")
    Call<AddCarIncResponse> addNewIncident(@Header("Authorization") String token, @Body AddCarIncRequest addCarIncRequest);

    //Статус оплаты за парковку
    @POST("ExtApi/CheckCar")
    Call<CheckCarResponse> getPaymentStatus(@Body CheckCarRequest paymentStatusRequest);

    //Запрос чека
    @POST("api/decision")
    Call<ReceiptResponse> getReceipt(@Header("Authorization") String token, @Body ReceiptRequest receiptRequest);

    //апрос Номера авто
    @POST("api/car_info")
    Call<ReceiptResponse> getCarInfo(@Header("Authorization") String token, @Body String carNumber);

    //Получение токена через from-data
    @FormUrlEncoded
    @POST("api/token")
    Call<TokenResponse> getTokenAccessPost(@Field("login") String superLogin, @Field("password") String superPassword);

    @FormUrlEncoded
    @POST("api/incident_upload_file")
    Call<UploadResponse> uploadPhoto(@Header("Authorization") String token, @Field("Guid") String guid, @Field("Base64StringContent") String file, @Field("PhotoPerspectiveCode")int photoPerspectiveCode);

}
