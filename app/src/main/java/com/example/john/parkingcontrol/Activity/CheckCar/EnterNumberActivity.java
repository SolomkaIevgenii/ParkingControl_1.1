package com.example.john.parkingcontrol.Activity.CheckCar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.john.parkingcontrol.R;

public class EnterNumberActivity extends AppCompatActivity {

    private SharedPreferences sPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_number);


        findViewById(R.id.buttonCheck).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v.setEnabled(false);

                EditText carNumber = findViewById(R.id.editTextCarNumber);
                String finalCarNumber = carNumber.getText().toString();
                String dataType = getResources().getString(R.string.sp_field_carNumber);

                Intent intent = new Intent(v.getContext(), CheckingResultActivity.class);
                intent.putExtra("finalCarNumber", finalCarNumber);
                startActivity(intent);
                v.setEnabled(true);
            }
        });
    }
}
