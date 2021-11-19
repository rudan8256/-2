package com.example.programing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class Mainlayout extends AppCompatActivity {

    private LinearLayout maplinear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainlayout);

        maplinear=findViewById(R.id.mapsearch);

        maplinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext() , MainActivity.class);
                startActivity(intent);
            }
        });
    }
}