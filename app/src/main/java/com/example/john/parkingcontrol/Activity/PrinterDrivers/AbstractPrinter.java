package com.example.john.parkingcontrol.Activity.PrinterDrivers;

import java.io.UnsupportedEncodingException;

public class AbstractPrinter {
    public static String getBluetoothName() {
        return "";
    }

    public static AbstractPrinter getPrinterByName(String deviceName){
        if(deviceName.equals(LK_P34.getBluetoothName() ) ) {
            return new LK_P34();
        }

        if( deviceName.equals(QSPrinter.getBluetoothName() ) ) {
            return new QSPrinter();
        }

        return null;
    }

    public byte[] getDataForPrint(String msg) throws UnsupportedEncodingException {
        return null;
    }
}
