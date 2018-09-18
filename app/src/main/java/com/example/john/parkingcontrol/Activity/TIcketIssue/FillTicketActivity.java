package com.example.john.parkingcontrol.Activity.TIcketIssue;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.john.parkingcontrol.API.interfaces.GetTokenApi;
import com.example.john.parkingcontrol.API.models.AddCarInc.AddCarIncRequest;
import com.example.john.parkingcontrol.API.models.AddCarInc.AddCarIncResponse;
import com.example.john.parkingcontrol.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class FillTicketActivity extends AppCompatActivity {
    private GetTokenApi service;
    private String myGuid;
    private SharedPreferences sPref;
    private String myToken;
    private String responseCarNumber, driverName, description, address, driverContact;
    private Double gpsLong, gpsLat;
    private Boolean isEmptyNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_ticket);
        Button button = findViewById(R.id.buttonSetIssue);

        myGuid = getIntent().getExtras().getString("guid");
        isEmptyNumber = getIntent().getExtras().getBoolean("isEmptyNumber");


        sPref = getSharedPreferences(getResources().getString(R.string.sp_folder_name), MODE_PRIVATE);
        myToken = "Bearer "+sPref.getString(getResources().getString(R.string.sp_field_token), "");
        if (!isEmptyNumber) {
            responseCarNumber = getIntent().getExtras().getString("responseCarNumber");
        }
        else if (isEmptyNumber){
            responseCarNumber = "";
        }
        gpsLong = 0.0d;
        gpsLat = 0.0d;
        driverName = findViewById(R.id.editDriverName).toString();
        driverContact = findViewById(R.id.editDriverContacts).toString();
        description = findViewById(R.id.editDescription).toString();
        address = findViewById(R.id.editAddress).toString();



        String url = getString(R.string.app_main_url);
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        //sPref = getSharedPreferences(getResources().getString(R.string.sp_folder_name), MODE_PRIVATE);
        //myGuid = sPref.getString(getResources().getString(R.string.sp_field_guid), "");

        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        //final String currentDate = dateFormat.format(Calendar.getInstance().getTime());

        service = retrofit.create(GetTokenApi.class);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //String incidentAddress = findViewById(R.id.editTextIncidentAddress).toString();

                AddCarIncRequest addCarIncRequest = new AddCarIncRequest();

                addCarIncRequest.setGuid(myGuid);
                addCarIncRequest.setCarNumber(responseCarNumber);
                addCarIncRequest.setCarDriverContacts(driverContact);
                addCarIncRequest.setCarDriverName(driverName);
                addCarIncRequest.setDescription(description);
                addCarIncRequest.setIncidentAddress(address);
                addCarIncRequest.setGpsLong(gpsLong);
                addCarIncRequest.setGpsLat(gpsLat);
                addCarIncRequest.setLawEnactmentId(1);
                addCarIncRequest.setDocumentTypeId(1);

                final Call<AddCarIncResponse> responseCall = service.addNewIncident(myToken, addCarIncRequest);

                responseCall.enqueue(new Callback<AddCarIncResponse>() {
                    @Override
                    public void onResponse(Call<AddCarIncResponse> call, Response<AddCarIncResponse> response) {

                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(FillTicketActivity.this);
                        builder.setTitle("Результат");
                        builder.setMessage(response.toString());
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        builder.setCancelable(false);
                        android.app.AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        v.setEnabled(true);


                    }

                    @Override
                    public void onFailure(Call<AddCarIncResponse> call, Throwable t) {

                    }
                });

            }
        });

    }
}
