package com.example.lab2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;


public class order_dialog extends DialogFragment implements DialogInterface.OnClickListener {

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        String str = (String)bundle.get("info");

        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity())
                .setTitle("Your order")
                .setPositiveButton("Submit", this)
                .setNeutralButton("Cancel", this)
                .setMessage(str);
        return adb.create();
    }

    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case Dialog.BUTTON_POSITIVE:
                Toast.makeText(getActivity(), "Your pizza is on its way!", Toast.LENGTH_SHORT).show();
                break;
            case Dialog.BUTTON_NEUTRAL:
                break;
        }
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }
}
