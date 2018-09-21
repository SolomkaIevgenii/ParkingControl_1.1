package com.example.john.parkingcontrol.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.john.parkingcontrol.API.interfaces.GetTokenApi;
import com.example.john.parkingcontrol.API.models.GetToken.TokenRequest;
import com.example.john.parkingcontrol.API.models.GetToken.TokenResponse;
import com.example.john.parkingcontrol.Activity.TIcketIssue.FillTicketActivity;
import com.example.john.parkingcontrol.DifferentHelpers.PrDialog;
import com.example.john.parkingcontrol.DifferentHelpers.TemporaryDataStorage;
import com.example.john.parkingcontrol.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private GetTokenApi service;
    @NonNull
    private String myToken, myName;
    private TemporaryDataStorage temporaryDataStorage = new TemporaryDataStorage();
    private SharedPreferences sPrefToken;
    private TextWatcher textWatcher;
    private EditText enteredLogin;
    private EditText enteredPassword;
    private ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        initDialog();
        final PrDialog prDialog = new PrDialog();
        prDialog.initDialog(getString(R.string.app_text_loading), this);

        enteredLogin = findViewById(R.id.editTextLogin);

        if (enteredLogin.getText().toString().length()>3){
            findViewById(R.id.buttonEnter).setEnabled(true);
        }

        ImageView imageView = findViewById(R.id.imageLogoMain);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, PrintActivity.class);
                startActivity(intent);
                /*Intent intent = new Intent(LoginActivity.this, FillTicketActivity.class);
                startActivity(intent);*/
                Toast.makeText(LoginActivity.this, "From Login to Print", Toast.LENGTH_SHORT).show();
            }
        });

        final TextView loginEntered = findViewById(R.id.editTextLogin);
        final TextView passwordEntered = findViewById(R.id.editTextPassword);
        enteredLogin = findViewById(R.id.editTextLogin);
        enteredPassword = findViewById(R.id.editTextPassword);

        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (loginEntered.getText().toString().length()>=3 && passwordEntered.getText().toString().length()>=3){
                    findViewById(R.id.buttonEnter).setEnabled(true);
                }
                else if (loginEntered.getText().toString().length()<3 || passwordEntered.getText().toString().length()<3){
                    findViewById(R.id.buttonEnter).setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        enteredLogin.addTextChangedListener(textWatcher);
        enteredPassword.addTextChangedListener(textWatcher);

        String url = getString(R.string.app_main_url);

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(GetTokenApi.class);


        View buttonEnter = findViewById(R.id.buttonEnter);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //showDialog();
                prDialog.showDialog();
                v.setEnabled(false);

                TokenRequest tokenRequest = new TokenRequest();

                //Импорт данных в клас, для отправки запроса в Json
                tokenRequest.setLogin(loginEntered.getText().toString());
                tokenRequest.setPassword(passwordEntered.getText().toString());


                //String superLogin = loginEntered.getText().toString();
                //String superPassword = passwordEntered.getText().toString();

                 /**
                 * Ниже вызов класа для формирования запроса
                 */

                final Call<TokenResponse> tokenRequestCall = service.getTokenAccess(tokenRequest);

                tokenRequestCall.enqueue(new Callback<TokenResponse>() {
                    @Override
                    public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                        prDialog.hideDialog();
                        v.setEnabled(true);

                        int statusCode = response.code();
                        switch (statusCode){
                            case 200:
                                try {
                                    myToken = response.body().getAccess_token();
                                    myName = response.body().getLogin();
                                }catch (NullPointerException e){
                                    Toast.makeText(LoginActivity.this, "Помилка доступу: "+e, Toast.LENGTH_SHORT).show();
                                }
                                sPrefToken = getSharedPreferences(getResources().getString(R.string.sp_folder_name), MODE_PRIVATE);
                                SharedPreferences.Editor ed = sPrefToken.edit();
                                ed.putString(getResources().getString(R.string.sp_field_token), myToken);
                                ed.commit();

                                sPrefToken = getSharedPreferences(getResources().getString(R.string.sp_folder_name), MODE_PRIVATE);
                                SharedPreferences.Editor edl = sPrefToken.edit();
                                edl.putString(getResources().getString(R.string.sp_field_user), myName);
                                edl.commit();


                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                                break;

                            case 400:
                                Toast.makeText(LoginActivity.this, "Login or/and password is incorrect"+response.code(), Toast.LENGTH_LONG).show();
                                break;

                            default:
                                Toast.makeText(LoginActivity.this, getResources().getString(R.string.app_an_error)+statusCode, Toast.LENGTH_LONG).show();
                                break;
                        }
                    }

                    @Override
                    public void onFailure(Call<TokenResponse> call, Throwable t) {
                        prDialog.hideDialog();
                        v.setEnabled(true);

                        Toast.makeText(LoginActivity.this, getResources().getString(R.string.app_an_internet_error), Toast.LENGTH_SHORT).show();

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
        else { Toast.makeText(this, "Натисніть повторно для ВИХОДУ", Toast.LENGTH_LONG).show(); }

        mBackPressed = System.currentTimeMillis();
    }

    protected void initDialog() {

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Завантаження...");
        pDialog.setCancelable(true);
    }


    protected void showDialog() {

        if (!pDialog.isShowing()) pDialog.show();
    }

    protected void hideDialog() {

        if (pDialog.isShowing()) pDialog.dismiss();
    }

}
