package com.example.john.parkingcontrol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


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

                /*!!!ПОКА НИКУДА НЕ ПЕРЕДАЮ!!!*/
                String login = userLogin.getText().toString();
                String password = userPassword.getText().toString();

                Intent intent = new Intent(v.getContext(), ParkingListActivity.class);
                intent.putExtra(ParkingListActivity.EXTRA_KEY_LOGIN, login);
                intent.putExtra(ParkingListActivity.EXTRA_KEY_PASSWORD, password);
                startActivity(intent);
                finish();
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
