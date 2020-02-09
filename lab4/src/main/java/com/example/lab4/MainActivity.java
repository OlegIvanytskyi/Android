package com.example.lab4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view)
    {
        Intent intent = null;
        switch(view.getId())
        {
            case R.id.audio:
                intent = new Intent(this, Audio.class);
                break;
            case R.id.video:
                intent = new Intent(this, Video.class);
                break;
        }
        startActivity(intent);
    }
}
