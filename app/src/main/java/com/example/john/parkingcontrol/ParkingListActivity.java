package com.example.john.parkingcontrol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ParkingListActivity extends AppCompatActivity {

    //Временная заглушка со статичным списком

    public static final String EXTRA_KEY_LOGIN = "Логин";
    public static final String EXTRA_KEY_PASSWORD = "Пароль";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_list);

        findViewById(R.id.textView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Button address = findViewById(R.id.textView);
                String addressGet = address.getText().toString();

                Intent intent = new Intent(v.getContext(), MainActivity.class);
                intent.putExtra(MainActivity.EXTRA_KEY_ADDRESS, addressGet);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.textView2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Button address1 = findViewById(R.id.textView2);
                String addressGet1 = address1.getText().toString();

                Intent intent = new Intent(v.getContext(), MainActivity.class);
                intent.putExtra(MainActivity.EXTRA_KEY_ADDRESS, addressGet1);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.textView3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Button adress1 = findViewById(R.id.textView3);
                String adressGet1 = adress1.getText().toString();

                Intent intent = new Intent(v.getContext(), MainActivity.class);
                intent.putExtra(MainActivity.EXTRA_KEY_ADDRESS, adressGet1);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.textView4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Button adress1 = findViewById(R.id.textView4);
                String adressGet1 = adress1.getText().toString();

                Intent intent = new Intent(v.getContext(), MainActivity.class);
                intent.putExtra(MainActivity.EXTRA_KEY_ADDRESS, adressGet1);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.textView5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Button adress1 = findViewById(R.id.textView5);
                String adressGet1 = adress1.getText().toString();

                Intent intent = new Intent(v.getContext(), MainActivity.class);
                intent.putExtra(MainActivity.EXTRA_KEY_ADDRESS, adressGet1);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.textView6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Button adress1 = findViewById(R.id.textView6);
                String adressGet1 = adress1.getText().toString();

                Intent intent = new Intent(v.getContext(), MainActivity.class);
                intent.putExtra(MainActivity.EXTRA_KEY_ADDRESS, adressGet1);
                startActivity(intent);
                finish();
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
