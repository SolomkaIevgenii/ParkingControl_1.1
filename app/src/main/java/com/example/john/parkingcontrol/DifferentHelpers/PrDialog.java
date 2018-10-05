package com.example.john.parkingcontrol.DifferentHelpers;

import android.app.ProgressDialog;
import android.content.Context;

public class PrDialog {

    ProgressDialog pDialog;

    public void initDialog(String message, Context actContext) {

        pDialog = new ProgressDialog(actContext);
        pDialog.setMessage(message);
        pDialog.setCancelable(false);
    }


    public void showDialog() {

        if (!pDialog.isShowing()) pDialog.show();
    }

    public void hideDialog() {

        if (pDialog.isShowing()) pDialog.dismiss();
    }
}
