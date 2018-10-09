package com.example.john.parkingcontrol.Activity.TIcketIssue;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.john.parkingcontrol.Activity.TIcketIssue.Photo.PhotoActivity;
import com.example.john.parkingcontrol.R;

public class DetailsProtokolActivity extends AppCompatActivity {

    private SharedPreferences sPref;
    private String myToken, myGuid, responseCarNumber, responseAddress;
    private Boolean isEmptyNumber;
    private Double gpsLat, gpsLon;
    private int prNumber, lawNumber;
    private TextView textName1, textName2, textName3, textName4, textName5, textName6, textMin1, textMin2, textMin3, textMin4, textMin5, textMin6, textMax1, textMax2, textMax3, textMax4, textMax5, textMax6;
    private CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6;
    private ScrollView scroll_1, scroll_2, scroll_3, scroll_4, scroll_5, scroll_6;
    private TextView textDesc1, textDesc2, textDesc3, textDesc4, textDesc5, textDesc6;
    private ImageView singleLine2, singleLine3, singleLine4, singleLine5, singleLine6;
    private Button buttonNext, buttonPrevious;;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_protokol);

        textName1 = findViewById(R.id.textName1);
        textName2 = findViewById(R.id.textName2);
        textName3 = findViewById(R.id.textName3);
        textName4 = findViewById(R.id.textName4);
        textName5 = findViewById(R.id.textName5);
        textName6 = findViewById(R.id.textName6);
        textMin1 = findViewById(R.id.textMin1);
        textMin2 = findViewById(R.id.textMin2);
        textMin3 = findViewById(R.id.textMin3);
        textMin4 = findViewById(R.id.textMin4);
        textMin5 = findViewById(R.id.textMin5);
        textMin6 = findViewById(R.id.textMin6);
        textMax1 = findViewById(R.id.textMax1);
        textMax2 = findViewById(R.id.textMax2);
        textMax3 = findViewById(R.id.textMax3);
        textMax4 = findViewById(R.id.textMax4);
        textMax5 = findViewById(R.id.textMax5);
        textMax6 = findViewById(R.id.textMax6);

        checkBox1 = findViewById(R.id.checkBox1);
        checkBox2 = findViewById(R.id.checkBox2);
        checkBox3 = findViewById(R.id.checkBox3);
        checkBox4 = findViewById(R.id.checkBox4);
        checkBox5 = findViewById(R.id.checkBox5);
        checkBox6 = findViewById(R.id.checkBox6);

        scroll_1 = findViewById(R.id.scroll_1);
        scroll_2 = findViewById(R.id.scroll_2);
        scroll_3 = findViewById(R.id.scroll_3);
        scroll_4 = findViewById(R.id.scroll_4);
        scroll_5 = findViewById(R.id.scroll_5);
        scroll_6 = findViewById(R.id.scroll_6);

        textDesc1 = findViewById(R.id.textDesc1);
        textDesc2 = findViewById(R.id.textDesc2);
        textDesc3 = findViewById(R.id.textDesc3);
        textDesc4 = findViewById(R.id.textDesc4);
        textDesc5 = findViewById(R.id.textDesc5);
        textDesc6 = findViewById(R.id.textDesc6);

        singleLine2 = findViewById(R.id.singleLine2);
        singleLine3 = findViewById(R.id.singleLine3);
        singleLine4 = findViewById(R.id.singleLine4);
        singleLine5 = findViewById(R.id.singleLine5);
        singleLine6 = findViewById(R.id.singleLine6);

        buttonNext = findViewById(R.id.buttonNext);
        buttonPrevious = findViewById(R.id.buttonPrevious);

        sPref = getSharedPreferences(getResources().getString(R.string.sp_folder_name), MODE_PRIVATE);
        myToken = sPref.getString(getResources().getString(R.string.sp_field_token), "");

        myGuid = getIntent().getExtras().getString("guid");
        isEmptyNumber = getIntent().getExtras().getBoolean("isEmptyNumber");
        responseCarNumber = getIntent().getExtras().getString("responseCarNumber");
        gpsLat = getIntent().getExtras().getDouble("gpsLat");
        gpsLon = getIntent().getExtras().getDouble("gpsLon");
        responseAddress = getIntent().getExtras().getString("responseAddress");
        prNumber = getIntent().getExtras().getInt("prNumber");

        if (prNumber==1){

            textName1.setText("Стаття №: 15.9 (а,б,в,г,г,д,з,е,є, и)");
            textName2.setText("Стаття №: 15.10  (а,б,г,е,є)");

            textMin1.setText("мін. штраф 255 грн.");
            textMin2.setText("мін. штраф 255 грн.");

            textMax1.setText("макс. штраф 255 грн.");
            textMax2.setText("макс. штраф 255 грн.");

            textDesc1.setText(getString(R.string.app_text_1_1159));
            textDesc2.setText(getString(R.string.app_text_1_21510));

            textName1.setVisibility(View.VISIBLE);
            textName2.setVisibility(View.VISIBLE);

            textMin1.setVisibility(View.VISIBLE);
            textMin2.setVisibility(View.VISIBLE);

            textMax1.setVisibility(View.VISIBLE);
            textMax2.setVisibility(View.VISIBLE);

            checkBox1.setVisibility(View.VISIBLE);
            checkBox2.setVisibility(View.VISIBLE);

            scroll_1.setVisibility(View.VISIBLE);
            scroll_2.setVisibility(View.VISIBLE);

            singleLine2.setVisibility(View.VISIBLE);

        }else if (prNumber==2){

            textName1.setText("Стаття №: 15.4");
            textName2.setText("Стаття №: 15.9 (а)");
            textName3.setText("Стаття №: 15.9 (б)");
            textName4.setText("Стаття №: 15.9 (в)");

            textMin1.setText("мін. штраф 510 грн.");
            textMin2.setText("мін. штраф 510 грн.");
            textMin3.setText("мін. штраф 510 грн.");
            textMin4.setText("мін. штраф 510 грн.");

            textMax1.setText("макс. штраф 510 грн.");
            textMax2.setText("макс. штраф 510 грн.");
            textMax3.setText("макс. штраф 510 грн.");
            textMax4.setText("макс. штраф 510 грн.");

            textDesc1.setText(getString(R.string.app_text_2_3154));
            textDesc2.setText(getString(R.string.app_text_2_4159));
            textDesc3.setText(getString(R.string.app_text_2_5159));
            textDesc4.setText(getString(R.string.app_text_2_6159));

            textName1.setVisibility(View.VISIBLE);
            textName2.setVisibility(View.VISIBLE);
            textName3.setVisibility(View.VISIBLE);
            textName4.setVisibility(View.VISIBLE);

            textMin1.setVisibility(View.VISIBLE);
            textMin2.setVisibility(View.VISIBLE);
            textMin3.setVisibility(View.VISIBLE);
            textMin4.setVisibility(View.VISIBLE);

            textMax1.setVisibility(View.VISIBLE);
            textMax2.setVisibility(View.VISIBLE);
            textMax3.setVisibility(View.VISIBLE);
            textMax4.setVisibility(View.VISIBLE);

            checkBox1.setVisibility(View.VISIBLE);
            checkBox2.setVisibility(View.VISIBLE);
            checkBox3.setVisibility(View.VISIBLE);
            checkBox4.setVisibility(View.VISIBLE);

            scroll_1.setVisibility(View.VISIBLE);
            scroll_2.setVisibility(View.VISIBLE);
            scroll_3.setVisibility(View.VISIBLE);
            scroll_4.setVisibility(View.VISIBLE);

            singleLine2.setVisibility(View.VISIBLE);
            singleLine3.setVisibility(View.VISIBLE);
            singleLine4.setVisibility(View.VISIBLE);

        }else if (prNumber==3){

            textName1.setText("ст.127 ч. 1");

            textMin1.setText("мін. штраф 1190 грн.");

            textMax1.setText("макс. штраф 1700 грн.");

            textDesc1.setText(getString(R.string.app_text_3_25));

            textName1.setVisibility(View.VISIBLE);

            textMin1.setVisibility(View.VISIBLE);

            textMax1.setVisibility(View.VISIBLE);

            checkBox1.setVisibility(View.VISIBLE);

            scroll_1.setVisibility(View.VISIBLE);


        }else if (prNumber==4){

            textName1.setText("ст.127 ч. 2");

            textMin1.setText("мін. штраф 850 грн.");

            textMax1.setText("макс. штраф 1190 грн.");

            textDesc1.setText(getString(R.string.app_text_4_26));

            textName1.setVisibility(View.VISIBLE);

            textMin1.setVisibility(View.VISIBLE);

            textMax1.setVisibility(View.VISIBLE);

            checkBox1.setVisibility(View.VISIBLE);

            scroll_1.setVisibility(View.VISIBLE);

        }else if (prNumber==5){

            textName1.setText("ст.127 ч. 3");

            textMin1.setText("мін. штраф 1700 грн.");

            textMax1.setText("макс. штраф 2040 грн.");

            textDesc1.setText(getString(R.string.app_text_5_27));

            textName1.setVisibility(View.VISIBLE);

            textMin1.setVisibility(View.VISIBLE);

            textMax1.setVisibility(View.VISIBLE);

            checkBox1.setVisibility(View.VISIBLE);

            scroll_1.setVisibility(View.VISIBLE);

        }else if (prNumber==6){

            textName1.setText("Пункт 26 Правил паркування т/з");

            textMin1.setText("мін. штраф 100 грн.");

            textMax1.setText("макс. штраф 100 грн.");

            textDesc1.setText(getString(R.string.app_text_6_20));

            textName1.setVisibility(View.VISIBLE);

            textMin1.setVisibility(View.VISIBLE);

            textMax1.setVisibility(View.VISIBLE);

            checkBox1.setVisibility(View.VISIBLE);

            scroll_1.setVisibility(View.VISIBLE);

        }else if (prNumber==7){

            textName1.setText("ст.152 ч.2");

            textMin1.setText("мін. штраф 150 грн.");

            textMax1.setText("макс. штраф 150 грн.");

            textDesc1.setText(getString(R.string.app_text_7_21));

            textName1.setVisibility(View.VISIBLE);

            textMin1.setVisibility(View.VISIBLE);

            textMax1.setVisibility(View.VISIBLE);

            checkBox1.setVisibility(View.VISIBLE);

            scroll_1.setVisibility(View.VISIBLE);

        }else if (prNumber==8){

            textName1.setText("п.13,14 Правил паркування т/з");

            textMin1.setText("мін. штраф 510 грн.");

            textMax1.setText("макс. штраф 850 грн.");

            textDesc1.setText(getString(R.string.app_text_8_22));

            textName1.setVisibility(View.VISIBLE);

            textMin1.setVisibility(View.VISIBLE);

            textMax1.setVisibility(View.VISIBLE);

            checkBox1.setVisibility(View.VISIBLE);

            scroll_1.setVisibility(View.VISIBLE);

        }else if (prNumber==9){

            textName1.setText("п.13,14 Правил паркування т/з");

            textMin1.setText("мін. штраф 680 грн.");

            textMax1.setText("макс. штраф 850 грн.");

            textDesc1.setText(getString(R.string.app_text_9_23));

            textName1.setVisibility(View.VISIBLE);

            textMin1.setVisibility(View.VISIBLE);

            textMax1.setVisibility(View.VISIBLE);

            checkBox1.setVisibility(View.VISIBLE);

            scroll_1.setVisibility(View.VISIBLE);

        }else if (prNumber==10){

            textName1.setText("п.13,14 Правил паркування т/з");

            textMin1.setText("мін. штраф 2040 грн.");

            textMax1.setText("макс. штраф 2550 грн.");

            textDesc1.setText(getString(R.string.app_text_10_24));

            textName1.setVisibility(View.VISIBLE);

            textMin1.setVisibility(View.VISIBLE);

            textMax1.setVisibility(View.VISIBLE);

            checkBox1.setVisibility(View.VISIBLE);

            scroll_1.setVisibility(View.VISIBLE);

        }else{
            Toast.makeText(this, "Помилка", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(DetailsProtokolActivity.this, ProtokolActivity.class);
            intent.putExtra("guid", myGuid);
            intent.putExtra("isEmptyNumber", isEmptyNumber);
            intent.putExtra("responseCarNumber", responseCarNumber);
            intent.putExtra("gpsLat", gpsLat);
            intent.putExtra("gpsLon", gpsLon);
            intent.putExtra("responseAddress", responseAddress);
            startActivity(intent);
            finish();
        }

        buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox1.isChecked()){
                    checkBox2.setEnabled(false);
                    checkBox3.setEnabled(false);
                    checkBox4.setEnabled(false);
                    checkBox5.setEnabled(false);
                    checkBox6.setEnabled(false);

                    buttonNext.setEnabled(true);

                    if (prNumber==1){
                        lawNumber=1;
                    }else if (prNumber==2){
                        lawNumber=2;
                    }else if (prNumber==3){
                        lawNumber=3;
                    }else if (prNumber==4){
                        lawNumber=4;
                    }else if (prNumber==5){
                        lawNumber=5;
                    }else if (prNumber==6){
                        lawNumber=6;
                    }else if (prNumber==7){
                        lawNumber=7;
                    }else if (prNumber==8){
                        lawNumber=8;
                    }else if (prNumber==9){
                        lawNumber=9;
                    }else if (prNumber==10){
                        lawNumber=10;
                    }

                    Intent intent = new Intent(DetailsProtokolActivity.this, FillTicketActivity.class);
                    intent.putExtra("guid", myGuid);
                    intent.putExtra("isEmptyNumber", isEmptyNumber);
                    intent.putExtra("responseCarNumber", responseCarNumber);
                    intent.putExtra("gpsLat", gpsLat);
                    intent.putExtra("gpsLon", gpsLon);
                    intent.putExtra("responseAddress", responseAddress);
                    intent.putExtra("lawNumber", lawNumber);
                    startActivity(intent);
                    finish();

                }else if (checkBox2.isChecked()){
                    checkBox1.setEnabled(false);
                    checkBox3.setEnabled(false);
                    checkBox4.setEnabled(false);
                    checkBox5.setEnabled(false);
                    checkBox6.setEnabled(false);

                    buttonNext.setEnabled(true);

                    if (prNumber==1){
                        lawNumber=1;
                    }else if (prNumber==2){
                        lawNumber=2;
                    }

                    Intent intent = new Intent(DetailsProtokolActivity.this, FillTicketActivity.class);
                    intent.putExtra("guid", myGuid);
                    intent.putExtra("isEmptyNumber", isEmptyNumber);
                    intent.putExtra("responseCarNumber", responseCarNumber);
                    intent.putExtra("gpsLat", gpsLat);
                    intent.putExtra("gpsLon", gpsLon);
                    intent.putExtra("responseAddress", responseAddress);
                    intent.putExtra("lawNumber", lawNumber);
                    startActivity(intent);
                    finish();
                }else if (checkBox3.isChecked()){
                    checkBox1.setEnabled(false);
                    checkBox2.setEnabled(false);
                    checkBox4.setEnabled(false);
                    checkBox5.setEnabled(false);
                    checkBox6.setEnabled(false);

                    buttonNext.setEnabled(true);

                    lawNumber = prNumber;


                    Intent intent = new Intent(DetailsProtokolActivity.this, FillTicketActivity.class);
                    intent.putExtra("guid", myGuid);
                    intent.putExtra("isEmptyNumber", isEmptyNumber);
                    intent.putExtra("responseCarNumber", responseCarNumber);
                    intent.putExtra("gpsLat", gpsLat);
                    intent.putExtra("gpsLon", gpsLon);
                    intent.putExtra("responseAddress", responseAddress);
                    intent.putExtra("lawNumber", lawNumber);
                    startActivity(intent);
                    finish();
                }else if (checkBox4.isChecked()){
                    checkBox1.setEnabled(false);
                    checkBox2.setEnabled(false);
                    checkBox3.setEnabled(false);
                    checkBox5.setEnabled(false);
                    checkBox6.setEnabled(false);

                    buttonNext.setEnabled(true);

                    lawNumber = prNumber;


                    Intent intent = new Intent(DetailsProtokolActivity.this, FillTicketActivity.class);
                    intent.putExtra("guid", myGuid);
                    intent.putExtra("isEmptyNumber", isEmptyNumber);
                    intent.putExtra("responseCarNumber", responseCarNumber);
                    intent.putExtra("gpsLat", gpsLat);
                    intent.putExtra("gpsLon", gpsLon);
                    intent.putExtra("responseAddress", responseAddress);
                    intent.putExtra("lawNumber", lawNumber);
                    startActivity(intent);
                    finish();
                }else if (checkBox5.isChecked()){
                    checkBox1.setEnabled(false);
                    checkBox2.setEnabled(false);
                    checkBox3.setEnabled(false);
                    checkBox4.setEnabled(false);
                    checkBox6.setEnabled(false);

                }else if (checkBox6.isChecked()){
                    checkBox1.setEnabled(false);
                    checkBox2.setEnabled(false);
                    checkBox3.setEnabled(false);
                    checkBox4.setEnabled(false);
                    checkBox5.setEnabled(false);
                }
            }
        });

        checkBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox1.isChecked()){
                    buttonNext.setEnabled(true);
                }else{
                    buttonNext.setEnabled(false);
                }
            }
        });

        checkBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox2.isChecked()){
                    buttonNext.setEnabled(true);
                }else{
                    buttonNext.setEnabled(false);
                }
            }
        });

        checkBox3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox3.isChecked()){
                    buttonNext.setEnabled(true);
                }else{
                    buttonNext.setEnabled(false);
                }
            }
        });

        checkBox4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox4.isChecked()){
                    buttonNext.setEnabled(true);
                }else{
                    buttonNext.setEnabled(false);
                }
            }
        });

        checkBox5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox5.isChecked()){
                    buttonNext.setEnabled(true);
                }else{
                    buttonNext.setEnabled(false);
                }
            }
        });

        checkBox6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox6.isChecked()){
                    buttonNext.setEnabled(true);
                }else{
                    buttonNext.setEnabled(false);
                }
            }
        });

    }



    @Override
    public void onBackPressed() {
    }
}
