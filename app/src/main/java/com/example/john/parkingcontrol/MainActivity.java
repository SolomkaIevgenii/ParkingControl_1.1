package com.example.john.parkingcontrol;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.john.parkingcontrol.ParkingPaymentCheck.CheckPaymentActivity;

public class MainActivity extends AppCompatActivity {


    @NonNull
    private String myToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonTicketIssue =  findViewById(R.id.buttonTicketIssue);
        Button buttonHistory =  findViewById(R.id.buttonHistory);


        SharedPreferences sPref;
        sPref = getSharedPreferences(getResources().getString(R.string.app_folder_name), MODE_PRIVATE);
        myToken = sPref.getString(getResources().getString(R.string.app_field_token), "");

        findViewById(R.id.buttonCheck).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);
                Intent intent = new Intent(v.getContext(), CheckPaymentActivity.class);
                startActivity(intent);
                v.setEnabled(true);
            }
        });

        buttonTicketIssue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);
                Toast.makeText(MainActivity.this, "Розділ у розробці", Toast.LENGTH_SHORT).show();
                v.setEnabled(true);
            }
        });

        buttonHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);
                Toast.makeText(MainActivity.this, "Розділ у розробці", Toast.LENGTH_SHORT).show();
                v.setEnabled(true);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        findViewById(R.id.buttonCheck).setEnabled(true);
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
