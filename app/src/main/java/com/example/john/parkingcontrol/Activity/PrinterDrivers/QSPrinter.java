package com.example.john.parkingcontrol.Activity.PrinterDrivers;
import java.io.UnsupportedEncodingException;

public class QSPrinter extends AbstractPrinter{
    public static String getBluetoothName() {
        return "QSPrinter";
    }

    public byte[] getDataForPrint(String msg) throws UnsupportedEncodingException {
        return msg.getBytes("windows-1251");
    }
}
