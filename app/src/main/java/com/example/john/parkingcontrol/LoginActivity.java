package com.example.john.parkingcontrol;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.john.parkingcontrol.API.interfaces.GetTokenApi;
import com.example.john.parkingcontrol.API.models.TokenRequest;
import com.example.john.parkingcontrol.API.models.TokenResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private GetTokenApi service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.68:32771/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(GetTokenApi.class);


        View buttonEnter = findViewById(R.id.buttonEnter);
        final EditText userLogin = findViewById(R.id.editTextLogin);
        final EditText userPassword = findViewById(R.id.editTextPassword);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                final TokenRequest tokenRequest = new TokenRequest();

                TextView loginEntered = findViewById(R.id.editTextLogin);
                TextView passwordEntered = findViewById(R.id.editTextPassword);
                /**
                 * Импорт данных в клас, для отправки запроса в Json
                tokenRequest.setLogin(loginEntered.getText().toString());
                tokenRequest.setPassword(passwordEntered.getText().toString());
                */

                String superLogin = loginEntered.getText().toString();
                String superPassword = passwordEntered.getText().toString();

                 /**
                 * Ниже вызов класадля формирования запроса
                 */

                final Call<TokenResponse> tokenRequestCall = service.getTokenAccessPost(superLogin, superPassword);

                tokenRequestCall.enqueue(new Callback<TokenResponse>() {
                    @Override
                    public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {

                        int statusCode = response.code();
                        switch (statusCode){
                            case 200:

                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setTitle("Номер авто: ");
                                builder.setMessage(" Code= "+response.code());
                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {



                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });

                                builder.setCancelable(false);
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();

                            case 400:

                                Toast.makeText(LoginActivity.this, "Login or/and password is incorrect", Toast.LENGTH_LONG).show();
                                break;

                            default:

                                Toast.makeText(LoginActivity.this, "Что-то пошло не так", Toast.LENGTH_LONG).show();
                                break;
                        }
                    }

                    @Override
                    public void onFailure(Call<TokenResponse> call, Throwable t) {

                        Toast.makeText(LoginActivity.this, "Ошибка соединения", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        };
        buttonEnter.setOnClickListener(clickListener);
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

    public void checkPayment(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Номер авто: ");
        builder.setMessage("555");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setCancelable(false);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
