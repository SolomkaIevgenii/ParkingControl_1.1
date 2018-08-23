package com.example.john.parkingcontrol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static String EXTRA_KEY_ADDRESS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(this, getIntent().getStringExtra(EXTRA_KEY_ADDRESS), Toast.LENGTH_SHORT).show();

        Button buttonT =  findViewById(R.id.buttonTicketIssue);
        buttonT.setEnabled(false);

        Button buttonH =  findViewById(R.id.buttonHistory);
        buttonH.setEnabled(false);

        findViewById(R.id.buttonCheck).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CheckPaymentActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.buttonTicketIssue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this, "Розділ у розробці", Toast.LENGTH_SHORT).show();

            }
        });

        findViewById(R.id.buttonHistory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this, "Розділ у розробці", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
