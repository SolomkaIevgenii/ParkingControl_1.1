package com.example.john.parkingcontrol.Activity.CheckCar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.example.john.parkingcontrol.R;

public class EnterNumberActivity extends AppCompatActivity {

    private SharedPreferences sPref;
    private EditText carNumber;
    private TextWatcher textWatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_number);

        carNumber = findViewById(R.id.editTextCarNumber);

        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (carNumber.getText().toString().length()>=2){
                    findViewById(R.id.buttonCheck).setEnabled(true);
                }
                else {
                    findViewById(R.id.buttonCheck).setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        carNumber.addTextChangedListener(textWatcher);


        findViewById(R.id.buttonCheck).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v.setEnabled(false);
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
