package com.example.lab1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    final int PIZZA_LIST = 1;
    final int SUBMIT = 2;

    EditText edit_name;
    EditText edit_phone_number;
    EditText edit_address;

    Button choose_pizza_button;
    TextView chosen_pizza_text;

    RadioGroup radio_group;
    CheckBox check_box;
    Button order_button;

    String[] pizzas = {"Texas", "Hawaiian", "Margarita", "Pepperoni", "BBQ"};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit_name = findViewById(R.id.edit_name);
        edit_phone_number = findViewById(R.id.edit_phone_number);
        edit_address = findViewById(R.id.edit_address);

        choose_pizza_button = findViewById(R.id.choose_pizza_button);
        chosen_pizza_text = findViewById(R.id.chosen_pizza_text);

        radio_group = findViewById(R.id.radioGroup);
        check_box = findViewById(R.id.checkBox);
        order_button = findViewById(R.id.order_button);
    }

    public void onclick(View v)
    {
        switch(v.getId())
        {
            case R.id.choose_pizza_button:
                showDialog(PIZZA_LIST);
                break;
            case R.id.order_button:
                int radioId = radio_group.getCheckedRadioButtonId();
                if(chosen_pizza_text.getText() == "")
                {
                    Toast.makeText(this, "Choose pizza first", Toast.LENGTH_SHORT).show();
                }
                else if(radioId == -1)
                {
                    Toast.makeText(this, "Choose size first", Toast.LENGTH_SHORT).show();
                }
                else if(edit_name.getText().toString().equals("") || edit_phone_number.getText().toString().equals("") || edit_address.getText().toString().equals(""))
                {
                    Toast.makeText(this, "You have to fill all the fields", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    showDialog(SUBMIT);
                }
                break;
        }
    }

    protected Dialog onCreateDialog(int id)
    {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        switch(id)
        {
            case 1:
                adb.setTitle("Choose your pizza");
                adb.setSingleChoiceItems(pizzas, 1, myClickListener);
                adb.setPositiveButton("Ok", myClickListener);
                adb.setNeutralButton("Cancel", myClickListener);
                break;
            case 2:
                adb.setTitle("Your order");
                adb.setMessage("");
                adb.setPositiveButton("Submit", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Toast.makeText(MainActivity.this, "Your pizza is on its way!", Toast.LENGTH_SHORT).show();
                    }
                });
                adb.setNeutralButton("Cancel", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                    }
                });
                break;
        }
        return adb.create();
    }

    protected void onPrepareDialog(int id, Dialog dialog)
    {
        if(id == 2)
        {
            int radioId = radio_group.getCheckedRadioButtonId();
            RadioButton radioButton = findViewById(radioId);

            String borders = check_box.isChecked() ? "Yes"  : "No";

            int price = CountPrice(radioButton.getText().toString(), borders);

            ((AlertDialog)dialog).setMessage("Your name: " + edit_name.getText() +
                    "\nYour phone: " + edit_phone_number.getText() +
                    "\nYour address: " + edit_address.getText() +
                    "\nPizza: " + chosen_pizza_text.getText() +
                    "\nSize: " + radioButton.getText() +
                    "\nCheese Borders: " + borders +
                    "\nTotal price: " + price + "$");
        }
    }

    DialogInterface.OnClickListener myClickListener = new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            ListView lv = ((AlertDialog) dialog).getListView();
            if (which == Dialog.BUTTON_POSITIVE)
            {
                chosen_pizza_text.setText(pizzas[lv.getCheckedItemPosition()]);
            }
        }
    };

    public int CountPrice(String size, String borders)
    {
        int total = 0;
        switch(size)
        {
            case "S":
                total += 10;
                break;
            case "M":
                total += 20;
                break;
            case "L":
                total += 30;
                break;
            case "XL":
                total += 40;
                break;
        }
        if(borders == "Yes")
        {
            total += 8;
        }
        return total;
    }
}
