package com.example.programing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class Mainlayout extends AppCompatActivity {

    private CardView maplinear,board,oylmpic_list,sport_list;
    private CardView profilecard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainlayout);

        maplinear=findViewById(R.id.mapsearch);
        profilecard=findViewById(R.id.profile_round);
        oylmpic_list= findViewById(R.id.oylmpic_list);
        sport_list = findViewById(R.id.sport_list);
        board=findViewById(R.id.board);

        profilecard.bringToFront();


        //시설찾기
        maplinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext() , MainActivity.class);
                startActivity(intent);
            }
        });

        //게시판
        board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext() , Board.class);
                startActivity(intent);
            }
        });


        //패럶림픽 종목
        oylmpic_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //스포츠 종목
        sport_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}