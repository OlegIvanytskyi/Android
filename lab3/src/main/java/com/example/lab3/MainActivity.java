package com.example.lab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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


public class MainActivity extends AppCompatActivity {

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
    Button show_database_button;
    Button clear_database_button;

    String[] pizzas = {"Texas", "Hawaiian", "Margarita", "Pepperoni", "BBQ"};

    DBHelper dbHelper;

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
        show_database_button = findViewById(R.id.show_database_button);
        clear_database_button = findViewById(R.id.clear_database_button);

        dbHelper = new DBHelper(this);
    }

    public void onclick(View v)
    {
        String name = edit_name.getText().toString();
        String phone_number = edit_phone_number.getText().toString();
        String address = edit_phone_number.getText().toString();
        String pizza = chosen_pizza_text.getText().toString();
        String size = getYear();
        String borders = check_box.isChecked() ? "Yes"  : "No";

        ContentValues cv = new ContentValues();
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        switch(v.getId())
        {
            case R.id.choose_pizza_button:
                showDialog(PIZZA_LIST);
                break;
            case R.id.order_button:
                if(chosen_pizza_text.getText().toString().equals(""))
                {
                    Toast.makeText(this, "Choose pizza first", Toast.LENGTH_SHORT).show();
                }
                else if(size.equals(""))
                {
                    Toast.makeText(this, "Choose size first", Toast.LENGTH_SHORT).show();
                }
                else if(edit_name.getText().toString().equals("") || edit_phone_number.getText().toString().equals("") || edit_address.getText().toString().equals(""))
                {
                    Toast.makeText(this, "You have to fill all the fields", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    cv.put("name", name);
                    cv.put("phone_number", phone_number);
                    cv.put("address", address);
                    cv.put("pizza", pizza);
                    cv.put("size", size);
                    cv.put("borders", borders);

                    db.insert("orders", null, cv);

                    showDialog(SUBMIT);
                }
                break;
            case R.id.show_database_button:
                Intent intent = new Intent(this, DatabaseResults.class);
                startActivity(intent);
                break;
            case R.id.clear_database_button:
                int clearCount = db.delete("orders", null, null);
                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + "orders" + "'");
                Toast.makeText(this, "Database cleared\n" + Integer.toString(clearCount) + " rows deleted", Toast.LENGTH_SHORT).show();
                break;
        }
        dbHelper.close();
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
                break;
            case 2:
                adb.setTitle("Your order");
                adb.setMessage("");
                adb.setPositiveButton("Submit", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Toast.makeText(MainActivity.this, "Order added to the database", Toast.LENGTH_SHORT).show();
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
            String size = getYear();
            String borders = check_box.isChecked() ? "Yes"  : "No";

            int price = CountPrice(size, borders);

            ((AlertDialog)dialog).setMessage("Your name: " + edit_name.getText() +
                    "\nYour phone: " + edit_phone_number.getText() +
                    "\nYour address: " + edit_address.getText() +
                    "\nPizza: " + chosen_pizza_text.getText() +
                    "\nSize: " + size +
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

    protected String getYear()
    {
        int radioId = radio_group.getCheckedRadioButtonId();
        if(radioId == -1)
        {
            return "";
        }
        return ((RadioButton)findViewById(radioId)).getText().toString();
    }

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
