package com.example.john.parkingcontrol.ParkingPaymentCheck;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.john.parkingcontrol.API.interfaces.GetTokenApi;
import com.example.john.parkingcontrol.API.models.PaymentStatusRequest;
import com.example.john.parkingcontrol.API.models.PaymentStatusResponse;
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

    private TextView resultView;
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

        resultView = findViewById(R.id.textViewResult);
        resultView.setText("loading...");
        resultView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        sendRequest();

        findViewById(R.id.buttonRefresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);
                resultView.setText("loading...");
                resultView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
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
        PaymentStatusRequest paymentStatusRequest = new PaymentStatusRequest();

        loadData(getResources().getString(R.string.app_field_carNumber));
        paymentStatusRequest.setCarnumber(carNumber);
        paymentStatusRequest.setToken(tokenStaticTemporary);
        final Call<PaymentStatusResponse> requestCall = service.getPaymentStatus(paymentStatusRequest);
        requestCall.enqueue(new Callback<PaymentStatusResponse>() {
            @Override
            public void onResponse(Call<PaymentStatusResponse> call, Response<PaymentStatusResponse> response) {

                switch (response.code()) {
                    case 200:
                        resultView = findViewById(R.id.textViewResult);
                        resultView.setText(response.body().getResponseMessage().toString());

                        if (!response.body().getOnparking()) {
                            findViewById(R.id.buttonRefresh).setVisibility(View.GONE);
                            findViewById(R.id.buttonOK).setVisibility(View.GONE);
                            findViewById(R.id.buttonTicketIssue).setVisibility(View.VISIBLE);
                        }

                        Toast.makeText(CheckingResultActivity.this, response.body().getOnparking().toString() + " " + response.body().getResponseMessage().toString(), Toast.LENGTH_SHORT).show();
                        break;


                    default:
                        Toast.makeText(CheckingResultActivity.this, getResources().getString(R.string.app_an_error) + " " + response.code(), Toast.LENGTH_SHORT).show();
                        break;


                }

            }

            @Override
            public void onFailure(Call<PaymentStatusResponse> call, Throwable t) {

            }

        });
    }
    private void loadData(String dataType){
        sPref = getSharedPreferences(getResources().getString(R.string.app_folder_name), MODE_PRIVATE);
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
        else { Toast.makeText(getBaseContext(), "Tap back button in order to exit", Toast.LENGTH_SHORT).show(); }

        mBackPressed = System.currentTimeMillis();
    }
    public void badResult(){
        loadData(getResources().getString(R.string.app_field_carNumber));
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