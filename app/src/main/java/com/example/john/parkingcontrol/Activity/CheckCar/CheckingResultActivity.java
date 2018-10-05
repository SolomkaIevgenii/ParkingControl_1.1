package com.example.john.parkingcontrol.Activity.CheckCar;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.john.parkingcontrol.API.interfaces.GetTokenApi;
import com.example.john.parkingcontrol.API.models.CheckCar.CheckCarRequest;
import com.example.john.parkingcontrol.API.models.CheckCar.CheckCarResponse;
import com.example.john.parkingcontrol.API.models.Guid.GuidResponse;
import com.example.john.parkingcontrol.Activity.LoginActivity;
import com.example.john.parkingcontrol.Activity.TIcketIssue.Photo.PhotoActivity;
import com.example.john.parkingcontrol.DifferentHelpers.PrDialog;
import com.example.john.parkingcontrol.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CheckingResultActivity extends AppCompatActivity {

    private GetTokenApi service;
    private SharedPreferences sPref;
    private String carNumber, myGuid, myToken;
    private Boolean isEmptyNumber = false;
    private String tokenStaticTemporary = "fdf909e4j3f03jikdsjfpsdg9sdfd0ifjsdik";

    private TextView resultView, status, hours, startTime, endTime, parkName, parkAddress;
    private PrDialog prDialog = new PrDialog();
    private DateFormat dateFormat = new SimpleDateFormat("dd.MM.yy"+" о "+"HH:mm");
    private Date starTtime, endTtime = null;
    private Calendar calendarStart, calendarEnd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checking_result);
        prDialog.initDialog(getString(R.string.app_text_loading), this);
        myGuid = getIntent().getExtras().getString("guid");

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://parking.2click.money/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(GetTokenApi.class);

        resultView = findViewById(R.id.textCarNResult);
        resultView.setText("loading...");
        resultView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        sendRequest();

        findViewById(R.id.buttonRefresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);
                sendRequest();
                v.setEnabled(true);
            }
        });

        findViewById(R.id.buttonOK).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        findViewById(R.id.buttonTicketIssue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);
                getGuid();
            }
        });

        findViewById(R.id.buttonGetBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });


    }

    public void sendRequest(){
        prDialog.showDialog();
        //Toast.makeText(CheckingResultActivity.this, "ЗАшел", Toast.LENGTH_SHORT).show();
        CheckCarRequest checkCarRequest = new CheckCarRequest();

        carNumber = getIntent().getExtras().getString("finalCarNumber");
        checkCarRequest.setCarnumber(carNumber);
        checkCarRequest.setToken(tokenStaticTemporary);
        Call<CheckCarResponse> requestCall = service.getPaymentStatus(checkCarRequest);
        requestCall.enqueue(new Callback<CheckCarResponse>() {
            @Override
            public void onResponse(Call<CheckCarResponse> call, Response<CheckCarResponse> response) {
                prDialog.hideDialog();

                resultView = findViewById(R.id.textCarNResult);
                hours = findViewById(R.id.textHours);
                parkName = findViewById(R.id.textParkingName);
                parkAddress = findViewById(R.id.textParkingAddress);
                startTime = findViewById(R.id.textStartTime);
                endTime = findViewById(R.id.textEndTime);
                status = findViewById(R.id.textParkStatus);


                switch (response.code()) {
                    case 200:

                        if (!response.body().getOnParking()&carNumber.length()>1) {
                            findViewById(R.id.buttonRefresh).setVisibility(View.GONE);
                            findViewById(R.id.buttonOK).setVisibility(View.GONE);
                            findViewById(R.id.buttonTicketIssue).setVisibility(View.VISIBLE);
                            findViewById(R.id.buttonGetBack).setEnabled(true);
                            resultView.setText(response.body().getMessage());
                            hours.setVisibility(View.GONE);
                            parkName.setVisibility(View.GONE);
                            parkAddress.setVisibility(View.GONE);
                            startTime.setVisibility(View.GONE);
                            endTime.setVisibility(View.GONE);
                            status.setVisibility(View.GONE);
                            break;
                        }else if(response.body().getMessage().equalsIgnoreCase("Пустий номер авто!")){

                            findViewById(R.id.buttonRefresh).setVisibility(View.GONE);
                            Button buttonOKtoBACK = findViewById(R.id.buttonOK);
                            buttonOKtoBACK.setText("Назад");
                            buttonOKtoBACK.setVisibility(View.VISIBLE);
                            findViewById(R.id.buttonGetBack).setVisibility(View.GONE);
                            break;

                        }else{

                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                            Date dStart = null;
                            Date dEnd = null;
                            try {
                                dStart = sdf.parse(response.body().getParkingStart());
                                dEnd = sdf.parse(response.body().getParkingStart());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            String formattedTimeStart = dateFormat.format(dStart);
                            String formattedTimeEnd = dateFormat.format(dEnd);

                            status.setText("Авто на парковці");
                            resultView.setText("Авто "+response.body().getCarNumber().toString());
                            hours.setText("Сплачено за: "+response.body().getPrepayHours()+" г.");
                            parkName.setText(response.body().getParkigName());
                            parkAddress.setText(response.body().getAddress());
                            startTime.setText("Заїхав: "+formattedTimeStart);
                            endTime.setText("Сплачено до: "+formattedTimeEnd);
                            findViewById(R.id.buttonRefresh).setVisibility(View.VISIBLE);
                            findViewById(R.id.buttonOK).setVisibility(View.VISIBLE);
                            findViewById(R.id.buttonGetBack).setVisibility(View.GONE);
                            break;
                        }

                    case 401:
                        findViewById(R.id.buttonGetBack).setEnabled(true);
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(CheckingResultActivity.this);
                        builder.setTitle("Помилка");
                        builder.setMessage("Помилка авторизації, бездіяльність більше 20 хв." +
                                "Вас буде перенаправлено на сторінку авторизації");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(CheckingResultActivity.this, LoginActivity.class);
                                startActivity(intent);
                                CheckingResultActivity.this.finish();
                                dialog.dismiss();
                            }
                        });
                        builder.setCancelable(false);
                        android.app.AlertDialog alertDialog = builder.create();
                        alertDialog.show();

                    default:
                        findViewById(R.id.buttonGetBack).setEnabled(true);
                        Toast.makeText(CheckingResultActivity.this, getResources().getString(R.string.app_an_error) + " " + response.code()+" Зверніться до адміністратора", Toast.LENGTH_SHORT).show();
                        break;


                }

            }

            @Override
            public void onFailure(Call<CheckCarResponse> call, Throwable t) {
                prDialog.hideDialog();
                findViewById(R.id.buttonGetBack).setEnabled(true);
                Toast.makeText(CheckingResultActivity.this, "Помилка зв`язку, перевірте інтернет з`єднання."+t, Toast.LENGTH_SHORT).show();

            }

        });
    }

    private void badResult(){
        isEmptyNumber=false;
        Intent intent = new Intent(CheckingResultActivity.this, PhotoActivity.class);
        intent.putExtra("guid", myGuid);
        intent.putExtra("isEmptyNumber", isEmptyNumber);
        intent.putExtra("responseCarNumber", carNumber.toUpperCase());
        startActivity(intent);
        findViewById(R.id.buttonTicketIssue).setEnabled(true);
        findViewById(R.id.buttonGetBack).setEnabled(true);
    }
    private void getGuid(){

        sPref = getSharedPreferences(getResources().getString(R.string.sp_folder_name), MODE_PRIVATE);
        myToken = sPref.getString(getResources().getString(R.string.sp_field_token), "");

        String url = getString(R.string.app_main_url);
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(GetTokenApi.class);
        final Call<GuidResponse> responseCallGuid = service.getGuid("Bearer "+myToken);

        responseCallGuid.enqueue(new Callback<GuidResponse>() {
            @Override
            public void onResponse(Call<GuidResponse> call, Response<GuidResponse> response) {
                if(response.code()==200){

                    myGuid= response.body().getGuid();
                    badResult();
                }
            }

            @Override
            public void onFailure(Call<GuidResponse> call, Throwable t) {
                findViewById(R.id.buttonGetBack).setEnabled(true);
            }
        });
    }
}