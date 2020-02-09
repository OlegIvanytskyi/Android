package com.example.lab2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity implements SelectionListener {

    public static ArrayList<String> pizzas = new ArrayList(Arrays.asList("Texas", "Hawaiian", "Margarita", "Pepperoni", "BBQ"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void selectItem(int position) {
        ((TextView)findViewById(R.id.chosen_pizza_text)).setText(pizzas.get(position));
    }

    public static int CountPrice(String size, String borders)
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
