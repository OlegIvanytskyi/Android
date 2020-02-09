package com.example.lab2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import java.util.List;

public class SingleChoiceDialogFragment extends DialogFragment {

    public static final String DATA = "pizzas";
    public static final String SELECTED = "selected";

    private SelectionListener listener;

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        listener = (SelectionListener)activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Bundle bundle = getArguments();

        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());

        dialog.setTitle("Choose your pizza");
        dialog.setPositiveButton("OK", new DialogButtonClickListener());

        List<String> list = (List<String>)bundle.get(DATA);
        int position = bundle.getInt(SELECTED);

        String[] pizzas = list.toArray(new String[list.size()]);
        dialog.setSingleChoiceItems(pizzas, position, selectItemListener);

        return dialog.create();
    }

    class DialogButtonClickListener implements DialogInterface.OnClickListener
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            dialog.dismiss();
        }
    }

    DialogInterface.OnClickListener selectItemListener = new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            if (listener != null)
            {
                listener.selectItem(which);
            }
        }

    };
}
