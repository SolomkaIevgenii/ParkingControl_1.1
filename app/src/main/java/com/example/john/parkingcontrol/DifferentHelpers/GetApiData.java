package com.example.john.parkingcontrol.DifferentHelpers;

import android.content.Context;

import com.example.john.parkingcontrol.API.interfaces.GetTokenApi;
import com.example.john.parkingcontrol.API.models.AddCarInc.AddressByGPS.AddressRequest;
import com.example.john.parkingcontrol.API.models.AddCarInc.AddressByGPS.AddressResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetApiData {
    private GetTokenApi service;
    private String myAddress;


    public String getTheAddress(Double gpsLatitude, Double gpsLongitude, String myToken, String url, Context context){

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(GetTokenApi.class);
        AddressRequest addressRequest = new AddressRequest();

        addressRequest.setLatitude(gpsLatitude);
        addressRequest.setLongitude(gpsLongitude);

        final Call<AddressResponse> responseCall = service.getAddress(myToken, addressRequest);
        responseCall.enqueue(new Callback<AddressResponse>() {
            @Override
            public void onResponse(Call<AddressResponse> call, Response<AddressResponse> response) {
                if (response.code()==200){
                    try{
                        myAddress = response.body().getAddress();
                         responseCall.cancel();
                        }
                        catch (NullPointerException e){
                        myAddress = "";
                            responseCall.cancel();
                        }
                }
                else {
                    myAddress = "";
                    responseCall.cancel();
                }
            }

            @Override
            public void onFailure(Call<AddressResponse> call, Throwable t) {
                myAddress = "";
                responseCall.cancel();
            }

        });
        return myAddress;
    }
}
