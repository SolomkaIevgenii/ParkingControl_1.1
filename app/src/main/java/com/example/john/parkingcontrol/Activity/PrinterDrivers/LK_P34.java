package com.example.john.parkingcontrol.Activity.PrinterDrivers;
import java.io.UnsupportedEncodingException;

public class LK_P34 extends AbstractPrinter{
    public static String getBluetoothName() {
        return "SW_3A6E";
    }

    public byte[] getDataForPrint(String msg) throws UnsupportedEncodingException {
        String src = msg;
        src = src.replace('і', 'i');
        src = src.replace('І', 'I');
        return src.getBytes("IBM866");
    }
}
