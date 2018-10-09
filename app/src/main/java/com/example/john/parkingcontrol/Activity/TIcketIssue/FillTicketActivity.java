package com.example.john.parkingcontrol.Activity.TIcketIssue;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.john.parkingcontrol.API.interfaces.GetTokenApi;
import com.example.john.parkingcontrol.API.models.AddCarInc.AddCarIncRequest;
import com.example.john.parkingcontrol.API.models.AddCarInc.AddCarIncResponse;
import com.example.john.parkingcontrol.Activity.LoginActivity;
import com.example.john.parkingcontrol.Activity.MainActivity;
import com.example.john.parkingcontrol.Activity.PrintActivity;
import com.example.john.parkingcontrol.DifferentHelpers.GetApiData;
import com.example.john.parkingcontrol.DifferentHelpers.PrDialog;
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
    private String responseCarNumber, responseAddress;
    private EditText driverName, description, address, driverContact, finalCarNumber;
    private Double gpsLon, gpsLat;
    private Boolean isEmptyNumber;
    private PrDialog prDialog = new PrDialog();
    private int lawNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_ticket);
        Button button = findViewById(R.id.buttonSetIssue);

        responseAddress = getIntent().getExtras().getString("responseAddress");
        gpsLon = getIntent().getExtras().getDouble("gpsLon");
        gpsLat = getIntent().getExtras().getDouble("gpsLat");
        myGuid = getIntent().getExtras().getString("guid");
        lawNumber = getIntent().getExtras().getInt("lawNumber");
        address = findViewById(R.id.editAddress);
        address.setText(responseAddress);

        isEmptyNumber = getIntent().getExtras().getBoolean("isEmptyNumber");
        finalCarNumber = findViewById(R.id.editCarNumberInc);

        sPref = getSharedPreferences(getResources().getString(R.string.sp_folder_name), MODE_PRIVATE);
        myToken = "Bearer "+sPref.getString(getResources().getString(R.string.sp_field_token), "");
        if (!isEmptyNumber) {
            responseCarNumber = getIntent().getExtras().getString("responseCarNumber");
            finalCarNumber.setEnabled(false);

        }
        else if (isEmptyNumber){
            responseCarNumber = "";
            finalCarNumber.setEnabled(true);
        }
        finalCarNumber.setText(responseCarNumber);



        String url = getString(R.string.app_main_url);
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");

        service = retrofit.create(GetTokenApi.class);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final String carNumber=finalCarNumber.getText().toString();
                v.setEnabled(false);
                driverName = findViewById(R.id.editDriverName);
                driverContact = findViewById(R.id.editDriverContacts);
                description = findViewById(R.id.editDescription);


                AddCarIncRequest addCarIncRequest = new AddCarIncRequest();

                addCarIncRequest.setGuid(myGuid);
                addCarIncRequest.setCarNumber(finalCarNumber.getText().toString());
                addCarIncRequest.setCarDriverContacts(driverContact.getText().toString());
                addCarIncRequest.setCarDriverName(driverName.getText().toString());
                addCarIncRequest.setDescription(description.getText().toString());
                addCarIncRequest.setIncidentAddress(address.getText().toString());
                addCarIncRequest.setGpsLong(gpsLon);
                addCarIncRequest.setGpsLat(gpsLat);
                addCarIncRequest.setLawEnactmentId(lawNumber);
                addCarIncRequest.setDocumentTypeId(1);

                final Call<AddCarIncResponse> responseCall = service.addNewIncident(myToken, addCarIncRequest);

                responseCall.enqueue(new Callback<AddCarIncResponse>() {
                    @Override
                    public void onResponse(Call<AddCarIncResponse> call, Response<AddCarIncResponse> response) {
                        if (response.code()==200) {
                            if (response.body().getIsSuccess()) {
                                Intent intent = new Intent(FillTicketActivity.this, PrintActivity.class);
                                intent.putExtra("guid", myGuid);
                                intent.putExtra("document_number", response.body().getDocument_number());
                                intent.putExtra("document_author", response.body().getDocument_author());
                                intent.putExtra("document_date", response.body().getDocument_date());
                                intent.putExtra("car_number", carNumber);
                                startActivity(intent);
                                finish();
                                v.setEnabled(true);
                            }
                            else{
                                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(FillTicketActivity.this);
                                builder.setTitle("Помилка");
                                builder.setMessage("Помилка створення інцеденту");
                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(FillTicketActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        FillTicketActivity.this.finish();
                                        dialog.dismiss();
                                    }
                                });
                                builder.setCancelable(false);
                                android.app.AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                                v.setEnabled(true);
                            }
                        }
                        else if (response.code()==401){
                            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(FillTicketActivity.this);
                            builder.setTitle("Помилка");
                            builder.setMessage("Помилка авторизації, бездіяльність більше 20 хв." +
                                    "Вас буде перенаправлено на сторінку авторизації");
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(FillTicketActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    FillTicketActivity.this.finish();
                                    dialog.dismiss();
                                }
                            });
                            builder.setCancelable(false);
                            android.app.AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                            v.setEnabled(true);
                        }

                        else{
                            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(FillTicketActivity.this);
                            builder.setTitle("Помилка");
                            builder.setMessage("Помилка на сервері." +
                                    "Зверніться до адміністратора.");
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(FillTicketActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    FillTicketActivity.this.finish();
                                    dialog.dismiss();
                                }
                            });
                            builder.setCancelable(false);
                            android.app.AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                            v.setEnabled(true);
                        }


                    }

                    @Override
                    public void onFailure(Call<AddCarIncResponse> call, Throwable t) {

                    }
                });

            }
        });

    }


    private String sendAdrRequest(){
        String url = getString(R.string.app_main_url);
        gpsLon = getIntent().getExtras().getDouble("gpsLon");
        gpsLat = getIntent().getExtras().getDouble("gpsLat");
        GetApiData getAddress = new GetApiData();
        return getAddress.getTheAddress(gpsLat, gpsLon, myToken, url, this);
    }
}
