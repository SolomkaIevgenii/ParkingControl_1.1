package com.example.john.parkingcontrol;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Set;
import java.util.UUID;

public class PrintActivity extends AppCompatActivity {

    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;
    OutputStream mmOutputStream;
    String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);

        try {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            if(mBluetoothAdapter == null) {
                Toast.makeText(this, "NoBTadapter", Toast.LENGTH_LONG).show();
            }

            if(!mBluetoothAdapter.isEnabled()) {
                Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBluetooth, 0);
            }

            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

            if(pairedDevices.size() > 0) {
                for (BluetoothDevice device : pairedDevices) {

                    // RPP300 is the name of the bluetooth printer device
                    // we got this name from the list of paired devices
                    if (device.getName().equals("QSPrinter")) {
                        mmDevice = device;
                        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
                        mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
                        mmSocket.connect();
                        mmOutputStream = mmSocket.getOutputStream();

                        Toast.makeText(this, "Bluetooth Opened", Toast.LENGTH_SHORT).show();

                        DateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
                        final String currentDate = dateFormat.format(Calendar.getInstance().getTime());

                        msg = "Билет на автобус\n" +
                                "\n" +
                                "дата:\t"+currentDate+"\n" +
                                "время:\t18:30\n" +
                                "Чек:\t13245679879\n" +
                                "серия:\tФФ1325ББ\n" +
                                "\n" +
                                "сумма:\t8.00грн.\n\n\n\n\n\n\n\n\n";

                        mmOutputStream.write(msg.getBytes("windows-1251"));

                        Toast.makeText(this, "BT sanded", Toast.LENGTH_SHORT).show();
                        mmOutputStream.close();
                        mmSocket.close();

                    }
                }
            }

            Toast.makeText(this, "Bluetooth device found.", Toast.LENGTH_LONG).show();;

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
