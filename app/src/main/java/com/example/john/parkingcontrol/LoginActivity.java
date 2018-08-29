package com.example.john.parkingcontrol;

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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://11111111.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(GetTokenApi.class);

        /*Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();*/

        /*Объявляем кнопку входа, логин и пароль*/
        View buttonEnter = findViewById(R.id.buttonEnter);
        final EditText userLogin = findViewById(R.id.editTextLogin);
        final EditText userPassword = findViewById(R.id.editTextPassword);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TokenRequest tokenRequest = new TokenRequest();

                TextView loginEntered = findViewById(R.id.editTextLogin);
                TextView passwordEntered = findViewById(R.id.editTextPassword);

                tokenRequest.setLogin(loginEntered.getText().toString());
                tokenRequest.setLogin(passwordEntered.getText().toString());

                Call<TokenResponse> tokenRequestCall = service.getTokenAccess(tokenRequest);

                tokenRequestCall.enqueue(new Callback<TokenResponse>() {
                    @Override
                    public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {

                        int statusCide = response.code();

                        TokenResponse tokenResponse = response.body();

                        String myToken = new TokenResponse().getToken();

                        Toast.makeText(LoginActivity.this, myToken, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<TokenResponse> call, Throwable t) {

                    }
                });
                /*
                //!!!ПОКА НИКУДА НЕ ПЕРЕДАЮ!!!
                String login = userLogin.getText().toString();
                String password = userPassword.getText().toString();

                Intent intent = new Intent(v.getContext(), ParkingListActivity.class);
                intent.putExtra(ParkingListActivity.EXTRA_KEY_LOGIN, login);
                intent.putExtra(ParkingListActivity.EXTRA_KEY_PASSWORD, password);
                startActivity(intent);
                finish();
                */
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
}
