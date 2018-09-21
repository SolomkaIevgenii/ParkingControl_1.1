package com.example.john.parkingcontrol.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.john.parkingcontrol.API.interfaces.GetTokenApi;
import com.example.john.parkingcontrol.API.models.Guid.GuidResponse;
import com.example.john.parkingcontrol.Activity.CheckCar.CheckPaymentActivity;
import com.example.john.parkingcontrol.Activity.CheckCar.EnterNumberActivity;
import com.example.john.parkingcontrol.Activity.TIcketIssue.FillTicketActivity;
import com.example.john.parkingcontrol.Activity.TIcketIssue.Photo.PhotoActivity;
import com.example.john.parkingcontrol.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {


    @NonNull
    private String myToken, myGuid;
    private SharedPreferences sPref;
    private GetTokenApi service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonTicketIssue =  findViewById(R.id.buttonTicketIssue);
        Button buttonHistory =  findViewById(R.id.buttonHistory);



        sPref = getSharedPreferences(getResources().getString(R.string.sp_folder_name), MODE_PRIVATE);
        myToken = sPref.getString(getResources().getString(R.string.sp_field_token), "");

        findViewById(R.id.buttonCheck).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);
                Intent intent = new Intent(v.getContext(), EnterNumberActivity.class);
                startActivity(intent);
                v.setEnabled(true);
            }
        });

        buttonTicketIssue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                            v.setEnabled(false);
                            Intent intent = new Intent(MainActivity.this, PhotoActivity.class);
                            intent.putExtra("guid", myGuid);
                            intent.putExtra("responseCarNumber", "");
                            intent.putExtra("isEmptyNumber", true);
                            startActivity(intent);
                            v.setEnabled(true);
            }
        });

        buttonHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    protected void onResume() {
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
                }else if (response.code()==401){
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Помилка");
                    builder.setMessage("Помилка авторизації, бездіяльність більше 20 хв." +
                            "Вас буде перенаправлено на сторінку авторизації");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                            MainActivity.this.finish();
                            dialog.dismiss();
                        }
                    });
                    builder.setCancelable(false);
                    android.app.AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }else{
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Помилка");
                    builder.setMessage("Помилка ана сервері, зверніться до адміністратора");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                            MainActivity.this.finish();
                            dialog.dismiss();
                        }
                    });
                    builder.setCancelable(false);
                    android.app.AlertDialog alertDialog = builder.create();
                    alertDialog.show();
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
        else { Toast.makeText(getBaseContext(), "Tap back button in order to exit", Toast.LENGTH_SHORT).show(); }

        mBackPressed = System.currentTimeMillis();
    }
}
