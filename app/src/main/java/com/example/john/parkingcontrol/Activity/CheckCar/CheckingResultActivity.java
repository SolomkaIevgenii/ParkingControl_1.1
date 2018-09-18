package com.example.john.parkingcontrol.Activity.CheckCar;

import android.app.AlertDialog;
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
import com.example.john.parkingcontrol.Activity.PrintActivity;
import com.example.john.parkingcontrol.Activity.TIcketIssue.Photo.PhotoActivity;
import com.example.john.parkingcontrol.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CheckingResultActivity extends AppCompatActivity {

    private GetTokenApi service;
    private SharedPreferences sPref;
    private String carNumber, myGuid, myToken, responseCarNumber;
    private Boolean isEmptyNumber = false;
    //private PaymentStatusRequest paymentStatusRequest = new PaymentStatusRequest();
    private String tokenStaticTemporary = "fdf909e4j3f03jikdsjfpsdg9sdfd0ifjsdik";

    private TextView resultView, status, hours, startTime, endTime, parkName, parkAddress;
    //private MyAsyncTask myAsyncTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checking_result);
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
                badResult();
            }
        });


    }

    public void sendRequest(){
        CheckCarRequest checkCarRequest = new CheckCarRequest();

        carNumber = getIntent().getExtras().getString("finalCarNumber");
        checkCarRequest.setCarnumber(carNumber);
        checkCarRequest.setToken(tokenStaticTemporary);
        final Call<CheckCarResponse> requestCall = service.getPaymentStatus(checkCarRequest);
        requestCall.enqueue(new Callback<CheckCarResponse>() {
            @Override
            public void onResponse(Call<CheckCarResponse> call, Response<CheckCarResponse> response) {

                resultView = findViewById(R.id.textCarNResult);
                hours = findViewById(R.id.textHours);
                parkName = findViewById(R.id.textParkingName);
                parkAddress = findViewById(R.id.textParkingAddres);
                startTime = findViewById(R.id.textStartTime);
                endTime = findViewById(R.id.textEndTime);
                status = findViewById(R.id.textParkStatus);


                switch (response.code()) {
                    case 200:

                        if (!response.body().getOnParking()) {
                            findViewById(R.id.buttonRefresh).setVisibility(View.GONE);
                            findViewById(R.id.buttonOK).setVisibility(View.GONE);
                            findViewById(R.id.buttonTicketIssue).setVisibility(View.VISIBLE);
                            resultView.setText(response.body().getMessage());
                            hours.setVisibility(View.GONE);
                            parkName.setVisibility(View.GONE);
                            parkAddress.setVisibility(View.GONE);
                            startTime.setVisibility(View.GONE);
                            endTime.setVisibility(View.GONE);
                            status.setVisibility(View.GONE);
                            responseCarNumber = response.body().getCarNumber();
                            break;
                        }else if(response.body().getMessage().toString().equalsIgnoreCase("Пустий номер авто!")){

                            findViewById(R.id.buttonRefresh).setVisibility(View.GONE);
                            Button buttonOKtoBACK = findViewById(R.id.buttonOK);
                            buttonOKtoBACK.setText("Назад");
                            buttonOKtoBACK.setVisibility(View.VISIBLE);
                            break;

                        }else{
                            status.setText("Авто на парковці");
                            resultView.setText("Авто "+response.body().getCarNumber().toString());
                            hours.setText("Сплачено за: "+response.body().getPrepayHours()+" г.");
                            parkName.setText(response.body().getParkigName());
                            parkAddress.setText(response.body().getAddress());
                            startTime.setText("Заїхав: "+response.body().getParkingStart());
                            endTime.setText("Сплачено до: "+response.body().getParkingEnd());
                            findViewById(R.id.buttonRefresh).setVisibility(View.VISIBLE);
                            findViewById(R.id.buttonOK).setVisibility(View.VISIBLE);
                            break;
                        }

                    default:
                        Toast.makeText(CheckingResultActivity.this, getResources().getString(R.string.app_an_error) + " " + response.code(), Toast.LENGTH_SHORT).show();
                        break;


                }

            }

            @Override
            public void onFailure(Call<CheckCarResponse> call, Throwable t) {

            }

        });
    }


    @Override
    protected void onResume() {
        super.onResume();

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

                    //sPref = getSharedPreferences(getResources().getString(R.string.sp_folder_name), MODE_PRIVATE);
                    //SharedPreferences.Editor ed = sPref.edit();
                    //ed.putString(getResources().getString(R.string.sp_field_guid), myGuid);
                    //ed.commit();
                }
            }

            @Override
            public void onFailure(Call<GuidResponse> call, Throwable t) {

            }
        });
    }

    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;

    @Override
    public void onBackPressed()
    {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
        {
            super.onBackPressed();
            return;
        }
        else { Toast.makeText(getBaseContext(), "Натиснить 'назад' повторно щоб повернутися в пропередне меню.", Toast.LENGTH_SHORT).show(); }

        mBackPressed = System.currentTimeMillis();
    }
    private void badResult(){
        isEmptyNumber=false;
        getGuid();
        Intent intent = new Intent(CheckingResultActivity.this, PhotoActivity.class);
        intent.putExtra("guid", myGuid);
        intent.putExtra("isEmptyNumber", isEmptyNumber);
        intent.putExtra("responseCarNumber", responseCarNumber);
        startActivity(intent);
    }
    private void getGuid(){

        super.onResume();
        findViewById(R.id.buttonCheck).setEnabled(true);

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

                    //sPref = getSharedPreferences(getResources().getString(R.string.sp_folder_name), MODE_PRIVATE);
                    //SharedPreferences.Editor ed = sPref.edit();
                    //ed.putString(getResources().getString(R.string.sp_field_guid), myGuid);
                    //ed.commit();
                }
            }

            @Override
            public void onFailure(Call<GuidResponse> call, Throwable t) {

            }
        });
    }
}





        /*
        resultView = findViewById(R.id.textViewResult);

        myAsyncTask = new MyAsyncTask(resultView);
        myAsyncTask.execute("http://parking.2click.money/ExtApi/carState?id="+getIntent().getStringExtra(enteredCarNumber)+"&accToken=fdf909e4j3f03jikdsjfpsdg9sdfd0ifjsdik");

        Toast.makeText(this, getIntent().getStringExtra(enteredCarNumber), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myAsyncTask.cancel(true);
    }

    public static class MyAsyncTask extends AsyncTask<String, Integer, String>{

        private TextView resultView;

        public MyAsyncTask(TextView resultView){
            this.resultView = resultView;
        }

        @Override
        protected String doInBackground(String... urls) {
            try {
                return new HttpClient().request(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            resultView.setText("Loading..."+values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s==null){
                Toast.makeText(resultView.getContext(), "Error", Toast.LENGTH_SHORT).show();
            }else {
                resultView.setText(s);
            }
        }
        */