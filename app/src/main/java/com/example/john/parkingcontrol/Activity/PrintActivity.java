package com.example.john.parkingcontrol.Activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.john.parkingcontrol.Activity.CheckCar.CheckingResultActivity;
import com.example.john.parkingcontrol.Activity.PrinterDrivers.AbstractPrinter;
import com.example.john.parkingcontrol.Activity.TIcketIssue.Photo.PhotoActivity;
import com.example.john.parkingcontrol.R;
import com.google.gson.annotations.Expose;

import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Set;
import java.util.UUID;

public class PrintActivity extends AppCompatActivity {

    static final UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    static final DateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);

        findViewById(R.id.buttonPrintMe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);
                int res = sendToPrint();
                v.setEnabled(true);
                Toast.makeText(PrintActivity.this, "Напечатано "+res, Toast.LENGTH_SHORT).show();
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
        BluetoothAdapter mBluetoothAdapter=null;
        BluetoothSocket mmSocket=null;
        OutputStream mmOutputStream=null;
        AbstractPrinter dev = null;
        String currentDate = dateFormat.format(Calendar.getInstance().getTime());
        String msg = "Билет на автобус\n" +
                "\n" +
                "дата:\t"+currentDate+"\n" +
                "время:\t18:30\n" +
                "Чек:\t13245679879\n" +
                "серия:\tФФ1325ББ\n" +
                "\n" +
                "сумма:\t8.00грн.\n\n\n\n\n\n\n\n\n";



        try {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            if(mBluetoothAdapter == null) {

                return 0;
            }

            if(!mBluetoothAdapter.isEnabled()) {
                Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBluetooth, 0);
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
}