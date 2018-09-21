package com.example.john.parkingcontrol.Activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.john.parkingcontrol.API.interfaces.GetTokenApi;
import com.example.john.parkingcontrol.API.models.AddCarInc.Receipt.ReceiptRequest;
import com.example.john.parkingcontrol.API.models.AddCarInc.Receipt.ReceiptResponse;
import com.example.john.parkingcontrol.Activity.CheckCar.CheckingResultActivity;
import com.example.john.parkingcontrol.Activity.PrinterDrivers.AbstractPrinter;
import com.example.john.parkingcontrol.Activity.TIcketIssue.FillTicketActivity;
import com.example.john.parkingcontrol.Activity.TIcketIssue.Photo.PhotoActivity;
import com.example.john.parkingcontrol.R;
import com.google.gson.annotations.Expose;

import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PrintActivity extends AppCompatActivity {

    static final UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    private BluetoothAdapter mBluetoothAdapter=null;
    private String msg, msgResponse, myGuid, document_number, document_author, document_date,car_number;
    private GetTokenApi service;
    private TextView documentNumber, documentAuthor, documentDate, carNumber;
    private DateFormat dateFormat = new SimpleDateFormat("dd.MM.yy"+" час "+"HH:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);
        myGuid = getIntent().getExtras().getString("guid");
        document_number = getIntent().getExtras().getString("document_number");
        document_author = getIntent().getExtras().getString("document_author");
        document_date = getIntent().getExtras().getString("document_date");
        car_number = getIntent().getExtras().getString("car_number");

        msg = "останова\n" +
                "\n" +
                "дата:\t"+"\n" +
                "время:\t18:30\n" +
                "Чек:\t13245679879\n" +
                "серия:\tФФ1325ББ\n" +
                "\n" +
                "сумма:\t8.00грн.\n\n\n\n\n\n\n\n\n";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date dStart = null;
        try {
            dStart = sdf.parse(document_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedDocumentDate = dateFormat.format(dStart);

        documentDate = findViewById(R.id.textViewDocumentDate);
        documentAuthor = findViewById(R.id.textViewInspectorName);
        documentNumber = findViewById(R.id.textViewDocumentNumber);
        carNumber = findViewById(R.id.textViewDocumentCarNumber);

        documentDate.setText(formattedDocumentDate);
        documentAuthor.setText(document_author);
        documentNumber.setText(document_number);
        carNumber.setText(car_number);

        String url = getString(R.string.app_main_url);
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(GetTokenApi.class);

        msg=getReceiptText();

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if(!mBluetoothAdapter.isEnabled()) {
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetooth, 0);
        }

        findViewById(R.id.buttonPrintMe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);
                int res = sendToPrint();
                v.setEnabled(true);
                Toast.makeText(PrintActivity.this, "Напечатано "+res+" "+msg, Toast.LENGTH_SHORT).show();
                //onBackPressed();
                }
        });
        findViewById(R.id.buttonClosePrinter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrintActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    private int sendToPrint(){
        int res = 0;
        mBluetoothAdapter=null;
        BluetoothSocket mmSocket=null;
        OutputStream mmOutputStream=null;
        AbstractPrinter dev = null;
        msg = getReceiptText();
//        String currentDate = dateFormat.format(Calendar.getInstance().getTime());
//        msg = "Билет на автобус\n" +
//                "\n" +
//                "дата:\t"+currentDate+"\n" +
//                "время:\t18:30\n" +
//                "Чек:\t13245679879\n" +
//                "серия:\tФФ1325ББ\n" +
//                "\n" +
//                "сумма:\t8.00грн.\n\n\n\n\n\n\n\n\n";



        try {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            if(mBluetoothAdapter == null) {

                return 0;
            }

            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

            if(pairedDevices.size() > 0) {
                for (BluetoothDevice device : pairedDevices) {
                    try{

                        dev = AbstractPrinter.getPrinterByName(device.getName());


                        if (dev!=null) {


                            mmSocket = device.createRfcommSocketToServiceRecord(uuid);
                            mmSocket.connect();
                            mmOutputStream = mmSocket.getOutputStream();



                            mmOutputStream.write(dev.getDataForPrint(msg));
                            res++;



                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }finally{
                        if (mmOutputStream!=null) {
                            mmOutputStream.close();
                        }

//                        if (mmSocket.isConnected()){
                        mmSocket.close();
//                        }

                        mmOutputStream=null;
                        mmSocket=null;
                        dev=null;
                    }
                }
            }

//            Toast.makeText(this, "Bluetooth device found.", Toast.LENGTH_LONG).show();;

        }catch(Exception e){
            e.printStackTrace();
        }

        return res;
    }
    private String getReceiptText(){
        ReceiptRequest receiptRequest = new ReceiptRequest();
        receiptRequest.setGuid(myGuid);
        receiptRequest.setFormat("txt");

        Call<ReceiptResponse> responseCall = service.getReceipt(receiptRequest);
        responseCall.enqueue(new Callback<ReceiptResponse>() {
            @Override
            public void onResponse(Call<ReceiptResponse> call, Response<ReceiptResponse> response) {
                msgResponse=response.body().getText();
            }

            @Override
            public void onFailure(Call<ReceiptResponse> call, Throwable t) {
                Toast.makeText(PrintActivity.this, " "+t, Toast.LENGTH_SHORT).show();
                msgResponse="Error";
            }
        });
        return msgResponse;
    }
}