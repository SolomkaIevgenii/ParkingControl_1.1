package com.example.john.parkingcontrol.Activity.TIcketIssue;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.john.parkingcontrol.Activity.TIcketIssue.Photo.PhotoActivity;
import com.example.john.parkingcontrol.R;

public class ProtokolActivity extends AppCompatActivity {

    private Button button1, button2, button3, button4, button5, button6, button7, button8, button9, button10;
    private SharedPreferences sPref;
    private String myToken, myGuid, responseCarNumber, responseAddress;
    private Boolean isEmptyNumber;
    private Double gpsLat, gpsLon;
    private int prNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protokol);
        sPref = getSharedPreferences(getResources().getString(R.string.sp_folder_name), MODE_PRIVATE);
        myToken = sPref.getString(getResources().getString(R.string.sp_field_token), "");

        myGuid = getIntent().getExtras().getString("guid");
        isEmptyNumber = getIntent().getExtras().getBoolean("isEmptyNumber");
        responseCarNumber = getIntent().getExtras().getString("responseCarNumber");
        gpsLat = getIntent().getExtras().getDouble("gpsLat");
        gpsLon = getIntent().getExtras().getDouble("gpsLon");
        responseAddress = getIntent().getExtras().getString("responseAddress");

        button1=findViewById(R.id.button1);
        button2=findViewById(R.id.button2);
        button3=findViewById(R.id.button3);
        button4=findViewById(R.id.button4);
        button5=findViewById(R.id.button5);
        button6=findViewById(R.id.button6);
        button7=findViewById(R.id.button7);
        button8=findViewById(R.id.button8);
        button9=findViewById(R.id.button9);
        button10=findViewById(R.id.button10);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prNumber=1;

                Intent intent = new Intent(ProtokolActivity.this, DetailsProtokolActivity.class);
                intent.putExtra("guid", myGuid);
                intent.putExtra("isEmptyNumber", isEmptyNumber);
                intent.putExtra("responseCarNumber", responseCarNumber);
                intent.putExtra("gpsLat", gpsLat);
                intent.putExtra("gpsLon", gpsLon);
                intent.putExtra("responseAddress", responseAddress);
                intent.putExtra("prNumber", prNumber);
                startActivity(intent);
                finish();

            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prNumber=2;

                Intent intent = new Intent(ProtokolActivity.this, DetailsProtokolActivity.class);
                intent.putExtra("guid", myGuid);
                intent.putExtra("isEmptyNumber", isEmptyNumber);
                intent.putExtra("responseCarNumber", responseCarNumber);
                intent.putExtra("gpsLat", gpsLat);
                intent.putExtra("gpsLon", gpsLon);
                intent.putExtra("responseAddress", responseAddress);
                intent.putExtra("prNumber", prNumber);
                startActivity(intent);
                finish();

            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prNumber=3;

                Intent intent = new Intent(ProtokolActivity.this, DetailsProtokolActivity.class);
                intent.putExtra("guid", myGuid);
                intent.putExtra("isEmptyNumber", isEmptyNumber);
                intent.putExtra("responseCarNumber", responseCarNumber);
                intent.putExtra("gpsLat", gpsLat);
                intent.putExtra("gpsLon", gpsLon);
                intent.putExtra("responseAddress", responseAddress);
                intent.putExtra("prNumber", prNumber);
                startActivity(intent);
                finish();

            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prNumber=4;

                Intent intent = new Intent(ProtokolActivity.this, DetailsProtokolActivity.class);
                intent.putExtra("guid", myGuid);
                intent.putExtra("isEmptyNumber", isEmptyNumber);
                intent.putExtra("responseCarNumber", responseCarNumber);
                intent.putExtra("gpsLat", gpsLat);
                intent.putExtra("gpsLon", gpsLon);
                intent.putExtra("responseAddress", responseAddress);
                intent.putExtra("prNumber", prNumber);
                startActivity(intent);
                finish();

            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prNumber=5;

                Intent intent = new Intent(ProtokolActivity.this, DetailsProtokolActivity.class);
                intent.putExtra("guid", myGuid);
                intent.putExtra("isEmptyNumber", isEmptyNumber);
                intent.putExtra("responseCarNumber", responseCarNumber);
                intent.putExtra("gpsLat", gpsLat);
                intent.putExtra("gpsLon", gpsLon);
                intent.putExtra("responseAddress", responseAddress);
                intent.putExtra("prNumber", prNumber);
                startActivity(intent);
                finish();

            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prNumber=6;

                Intent intent = new Intent(ProtokolActivity.this, DetailsProtokolActivity.class);
                intent.putExtra("guid", myGuid);
                intent.putExtra("isEmptyNumber", isEmptyNumber);
                intent.putExtra("responseCarNumber", responseCarNumber);
                intent.putExtra("gpsLat", gpsLat);
                intent.putExtra("gpsLon", gpsLon);
                intent.putExtra("responseAddress", responseAddress);
                intent.putExtra("prNumber", prNumber);
                startActivity(intent);
                finish();

            }
        });
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prNumber=7;

                Intent intent = new Intent(ProtokolActivity.this, DetailsProtokolActivity.class);
                intent.putExtra("guid", myGuid);
                intent.putExtra("isEmptyNumber", isEmptyNumber);
                intent.putExtra("responseCarNumber", responseCarNumber);
                intent.putExtra("gpsLat", gpsLat);
                intent.putExtra("gpsLon", gpsLon);
                intent.putExtra("responseAddress", responseAddress);
                intent.putExtra("prNumber", prNumber);
                startActivity(intent);
                finish();

            }
        });
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prNumber=8;

                Intent intent = new Intent(ProtokolActivity.this, DetailsProtokolActivity.class);
                intent.putExtra("guid", myGuid);
                intent.putExtra("isEmptyNumber", isEmptyNumber);
                intent.putExtra("responseCarNumber", responseCarNumber);
                intent.putExtra("gpsLat", gpsLat);
                intent.putExtra("gpsLon", gpsLon);
                intent.putExtra("responseAddress", responseAddress);
                intent.putExtra("prNumber", prNumber);
                startActivity(intent);
                finish();

            }
        });
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prNumber=9;

                Intent intent = new Intent(ProtokolActivity.this, DetailsProtokolActivity.class);
                intent.putExtra("guid", myGuid);
                intent.putExtra("isEmptyNumber", isEmptyNumber);
                intent.putExtra("responseCarNumber", responseCarNumber);
                intent.putExtra("gpsLat", gpsLat);
                intent.putExtra("gpsLon", gpsLon);
                intent.putExtra("responseAddress", responseAddress);
                intent.putExtra("prNumber", prNumber);
                startActivity(intent);
                finish();

            }
        });
        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prNumber=10;

                Intent intent = new Intent(ProtokolActivity.this, DetailsProtokolActivity.class);
                intent.putExtra("guid", myGuid);
                intent.putExtra("isEmptyNumber", isEmptyNumber);
                intent.putExtra("responseCarNumber", responseCarNumber);
                intent.putExtra("gpsLat", gpsLat);
                intent.putExtra("gpsLon", gpsLon);
                intent.putExtra("responseAddress", responseAddress);
                intent.putExtra("prNumber", prNumber);
                startActivity(intent);
                finish();

            }
        });
    }

    @Override
    public void onBackPressed() {
    }
}
