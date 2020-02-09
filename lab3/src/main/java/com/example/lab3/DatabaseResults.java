package com.example.lab3;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class DatabaseResults extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_results);

        ListView listView = findViewById(R.id.listView);

        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor c = db.query("orders", null, null, null, null, null, null);

        ArrayList<String> list = new ArrayList();

        if (c.moveToFirst()) {

            int idColIndex = c.getColumnIndex("id");
            int nameColIndex = c.getColumnIndex("name");
            int phoneColIndex = c.getColumnIndex("phone_number");
            int addressColIndex = c.getColumnIndex("address");
            int pizzaColIndex = c.getColumnIndex("pizza");
            int sizeColIndex = c.getColumnIndex("size");
            int bordersColIndex = c.getColumnIndex("borders");

            String border = c.getString(bordersColIndex).equals("Yes") ? "with cheese borders" : "without cheese borders";

            do {
                list.add(c.getInt(idColIndex) + ": " + c.getString(nameColIndex) + " (phone number: " + c.getString(phoneColIndex)
                + "; address: " + c.getString(addressColIndex) + ") ordered " + c.getString(sizeColIndex) + " size " + c.getString(pizzaColIndex) + " pizza " + border);
            } while (c.moveToNext());
        }
        else {
            list.add("no orders yet");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

        c.close();
        dbHelper.close();
    }
}
