package com.example.john.parkingcontrol.Activity.TIcketIssue;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.john.parkingcontrol.API.interfaces.GetTokenApi;
import com.example.john.parkingcontrol.API.models.AddCarInc.AddCarIncRequest;
import com.example.john.parkingcontrol.API.models.AddCarInc.AddCarIncResponse;
import com.example.john.parkingcontrol.API.models.Guid.GuidResponse;
import com.example.john.parkingcontrol.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class FillTicketActivity extends AppCompatActivity {
    private SharedPreferences sPref;
    private GetTokenApi service;
    private Boolean isEmptyNumber;
    //private String myGuid;
    private String myToken, myGuid, responseCarNumber;
    private EditText enteredNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_ticket);
        Button button = findViewById(R.id.buttonSetIssue);

        myGuid = getIntent().getExtras().getString("guid");
        isEmptyNumber = getIntent().getExtras().getBoolean("isEmptyNumber");
        responseCarNumber = getIntent().getExtras().getString("responseCarNumber");

        String url = getString(R.string.app_main_url);
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        if (!isEmptyNumber){
            enteredNumber = findViewById(R.id.inputCarNumberInc);
            enteredNumber.setText(responseCarNumber);
            enteredNumber.setEnabled(false);
            //TODO Задать полям inputType, создать переход в АКТИВИТИ печати чека, сделать запрос чека, поправить везде логотип 18.09.2018
        }
        else if (isEmptyNumber){
            enteredNumber = findViewById(R.id.inputCarNumberInc);
            enteredNumber.setText(responseCarNumber);
        }


        //sPref = getSharedPreferences(getResources().getString(R.string.sp_folder_name), MODE_PRIVATE);
        //myGuid = sPref.getString(getResources().getString(R.string.sp_field_guid), "");

        myGuid = getIntent().getExtras().getString("guid");
        Toast.makeText(this, "Intent guid = "+myGuid, Toast.LENGTH_SHORT).show();

        DateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        final String currentDate = dateFormat.format(Calendar.getInstance().getTime());

        sPref = getSharedPreferences(getResources().getString(R.string.sp_folder_name), MODE_PRIVATE);
        final String carN = sPref.getString(getResources().getString(R.string.sp_field_carNumber), "");

        sPref = getSharedPreferences(getResources().getString(R.string.sp_folder_name), MODE_PRIVATE);
        service = retrofit.create(GetTokenApi.class);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String incidentAddress = findViewById(R.id.editTextIncidentAddress).toString();

                AddCarIncRequest addCarIncRequest = new AddCarIncRequest();

                addCarIncRequest.setCarNumber(carN);
                addCarIncRequest.setCarNumber("222");
                addCarIncRequest.setDescription("222");
                addCarIncRequest.setCarDriverContacts("222");
                addCarIncRequest.setGpsLong(0.0);

                final Call<AddCarIncResponse> responseCall = service.addNewIncident(myToken, addCarIncRequest);

                responseCall.enqueue(new Callback<AddCarIncResponse>() {
                    @Override
                    public void onResponse(Call<AddCarIncResponse> call, Response<AddCarIncResponse> response) {

                    }

                    @Override
                    public void onFailure(Call<AddCarIncResponse> call, Throwable t) {

                    }
                });

            }
        });

    }
}
