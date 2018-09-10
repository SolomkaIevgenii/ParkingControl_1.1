package com.example.john.parkingcontrol.Activity.CheckCar;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.example.john.parkingcontrol.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CheckingResultActivity extends AppCompatActivity {

    private GetTokenApi service;
    private SharedPreferences sPref;
    private String carNumber;
    //private PaymentStatusRequest paymentStatusRequest = new PaymentStatusRequest();
    private String tokenStaticTemporary = "fdf909e4j3f03jikdsjfpsdg9sdfd0ifjsdik";

    private TextView resultView, status, hours, startTime, endTime, parkName, parkAddress;
    //private MyAsyncTask myAsyncTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checking_result);

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

        loadData(getResources().getString(R.string.sp_field_carNumber));
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
                            break;
                        }else if(response.body().getMessage().toString().equalsIgnoreCase("Пустий номер авто!")){

                            findViewById(R.id.buttonRefresh).setVisibility(View.GONE);
                            Button buttonOKtoBACK = findViewById(R.id.buttonOK);
                            buttonOKtoBACK.setText("Назад");
                            break;

                        }else{
                            status.setText("Авто на парковці");
                            resultView.setText("Авто "+response.body().getCarNumber().toString());
                            hours.setText("Сплачено за: "+response.body().getPrepayHours()+" г.");
                            parkName.setText(response.body().getParkigName());
                            parkAddress.setText(response.body().getAddress());
                            startTime.setText("Заїхав: "+response.body().getParkingStart());
                            endTime.setText("Оплачено до: "+response.body().getParkingEnd());
                        }




                        Toast.makeText(CheckingResultActivity.this, response.body().getOnParking().toString() + " " + response.body().getMessage().toString(), Toast.LENGTH_SHORT).show();
                        break;


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
    private void loadData(String dataType){
        sPref = getSharedPreferences(getResources().getString(R.string.sp_folder_name), MODE_PRIVATE);
        carNumber = sPref.getString(dataType, "");
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
    public void badResult(){
        loadData(getResources().getString(R.string.sp_field_carNumber));
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Номер авто: "+carNumber);
        builder.setMessage("Розділ у розробці. Натисніть ОК для переходу в попередне меню");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setCancelable(false);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
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