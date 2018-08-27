package com.example.john.parkingcontrol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EnterNumberActivity extends AppCompatActivity {

    //EditText carNumber;
    //String finalCarNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_number);


        findViewById(R.id.buttonCheck).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText carNumber = findViewById(R.id.editTextCarNumber);
                String finalCarNumber = carNumber.getText().toString();

                Intent intent = new Intent(v.getContext(), CheckingResultActivity.class);
                intent.putExtra(CheckingResultActivity.enteredCarNumber, finalCarNumber);
                startActivity(intent);
            }
        });
    }
}
